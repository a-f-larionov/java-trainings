package demo002.autowiring;

import demo002.autowiring.by_annotations.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

public class ByAnnotationsTest {

    @Test
    public void noCandidates() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(BeanParentWithAutowired.class);

        // given - when - then
        assertThatException().isThrownBy(cntx::refresh)
                .isInstanceOf(BeanCreationException.class)
                .withMessage("Error creating bean with name '" + getBeanName(BeanParentWithAutowired.class) + "': " +
                        "Unsatisfied dependency expressed through field 'beanChild': " +
                        "No qualifying bean of type '" + BeanChildInterface.class.getName() + "' available: " +
                        "expected at least 1 bean which qualifies as autowire candidate. " +
                        "Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}");
        cntx.close();
    }

    @Test
    public void resolveSingle() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(
                BeanParentWithAutowired.class,
                BeanChildA.class);

        // when - then
        assertThat(refreshCntxAndGetBeanChild(cntx))
                .isInstanceOf(BeanChildA.class);
    }

    @Test
    public void resolveTwoAmbiguityBeans() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(
                BeanParentWithAutowired.class,
                BeanChildA.class,
                BeanChildB.class);

        // when - then
        assertThatException().isThrownBy(cntx::refresh)
                .isInstanceOf(UnsatisfiedDependencyException.class)
                .withMessageContaining("Error creating bean with name 'beanParent': ")

                .havingCause()
                .isInstanceOf(NoUniqueBeanDefinitionException.class)
                .withMessage("No qualifying bean of type '" + BeanChildInterface.class.getName() + "' available: " +
                        "expected single matching bean but found 2: " + candidatesToSTring(",", BeanChildA.class, BeanChildB.class));
        cntx.close();
    }

    @Test
    public void resolveTwoAmbiguityButPrimary() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(
                BeanParentWithAutowired.class,
                BeanChildA.class,
                BeanChildAPrimary.class);

        assertThat(refreshCntxAndGetBeanChild(cntx)).isInstanceOf(BeanChildAPrimary.class);
    }

    @Test
    public void resolveThreeAmbiguityAndDoublePrimary() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(
                BeanParentWithAutowired.class,
                BeanChildAPrimary.class,
                BeanChildBPrimary.class,
                BeanChildC.class);

        // when - then
        assertThatException().isThrownBy(cntx::refresh)
                .isInstanceOf(UnsatisfiedDependencyException.class)
                .withMessageContaining("Error creating bean with name 'beanParent': ")

                .havingCause()
                .isInstanceOf(NoUniqueBeanDefinitionException.class)
                .withMessage("No qualifying bean of type '" + BeanChildInterface.class.getName() + "' available: " +
                        "more than one 'primary' bean found among candidates: [" +
                        candidatesToSTring(", ", BeanChildAPrimary.class, BeanChildBPrimary.class, BeanChildC.class)
                        + "]");
        cntx.close();
    }

    @Test
    public void resolveAmbiguityByQualifierName() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(
                BeanParentAutowiredQualifier.class,
                BeanChildWithQuilifierName.class,
                BeanChildA.class);

        // when - then
        assertThat(refreshCntxAndGetBeanChild(cntx))
                .isInstanceOf(BeanChildWithQuilifierName.class);
    }

    @Test
    public void resolveByPropNameEqualsBeanNameWithAmbiguity() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(
                BeanParentWithAutowired.class,
                BeanChildA.class,
                BeanChildB.class
        );
        var bd = new GenericBeanDefinition();
        bd.setBeanClass(BeanChildC.class);
        cntx.registerBeanDefinition("beanChild", bd);

        // when - then
        assertThat(refreshCntxAndGetBeanChild(cntx))
                .isInstanceOf(BeanChildC.class);
    }

    @Test
    public void excludeByProfileValue() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(BeanChildWithProfileValue.class);
        cntx.refresh();

        // when - then
        assertThat(cntx.getBeanNamesForType(BeanChildInterface.class))
                .hasSize(0);
        cntx.close();
    }

    @Test
    public void skipRequiredFalseNoCandidates() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(BeanParentAutowiredRequiredFalse.class);

        // when - then
        assertThat(refreshCntxAndGetBeanChild(cntx)).isNull();
    }

    @Test
    public void skipRrequiredFalseSingle() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(
                BeanParentAutowiredRequiredFalse.class,
                BeanChildA.class);

        // when - then
        assertThat(refreshCntxAndGetBeanChild(cntx))
                .isInstanceOf(BeanChildA.class);
    }

    @Test
    public void skipRequiredFalseAmbiguity() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(
                BeanParentAutowiredRequiredFalse.class,
                BeanChildA.class,
                BeanChildB.class);

        // when - then
        assertThatException().isThrownBy(() -> refreshCntxAndGetBeanChild(cntx))
                .isInstanceOf(BeanCreationException.class)

                .havingCause()
                .isInstanceOf(NoUniqueBeanDefinitionException.class);
        cntx.close();
    }

    @Test
    public void skipByNullable() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(BeanParentWithAutowiredNullable.class);
        // when - then
        assertThat(refreshCntxAndGetBeanChild(cntx))
                .isNull();
    }

    private String getBeanName(Class<?> clazz) {
        Component annotation = clazz.getAnnotation(Component.class);
        if (annotation != null && !annotation.value().isEmpty()) return annotation.value();
        var name = clazz.getSimpleName();
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    private BeanChildInterface refreshCntxAndGetBeanChild(AnnotationConfigApplicationContext cntx) {
        cntx.refresh();
        return cntx
                .getBean("beanParent", BeanParentInterface.class)
                .getBeanChild();
    }

    private String candidatesToSTring(String delimiter, Class<?>... candidates) {
        return stream(candidates)
                .map(this::getBeanName)
                .collect(Collectors.joining(delimiter));
    }
}
