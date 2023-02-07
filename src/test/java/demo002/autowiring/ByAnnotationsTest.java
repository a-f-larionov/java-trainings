package demo002.autowiring;

import demo002.autowiring.by_annotations.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

public class ByAnnotationsTest {

    @Test
    public void noChilds() {
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
    public void oneChild() {
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
    public void twoAmbiguityBeans() {
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
    public void twoAmbiguityButPrimary() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(
                BeanParentWithAutowired.class,
                BeanChildA.class,
                BeanChildAPrimary.class);

        assertThat(refreshCntxAndGetBeanChild(cntx)).isInstanceOf(BeanChildAPrimary.class);
    }

    @Test
    public void threeAmbiguityAndDoublePrimary() {
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
    public void autowireAmbiguiteResolveByPropertyName() {
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
    public void childAQualifier() {

    }

    @Test
    public void excludeAutowiringCandidateFalse() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(
                BeanParentWithAutowired.class,
                BeanChildA.class,
                BeanChildB.class);
        cntx.getBeanDefinition("beanChildA").setAutowireCandidate(false);
        // when -
        assertThat(refreshCntxAndGetBeanChild(cntx))
                .isInstanceOf(BeanChildB.class);
    }

    @Test
    public void excludeBeanByNoProfile() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(BeanChildANoProfiles.class);
        cntx.refresh();

        // when - then
        assertThat(cntx.getBeanNamesForType(BeanChildInterface.class))
                .hasSize(0);
        cntx.close();
    }

    @Test
    public void requiredFalseNoOneCandidate() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(BeanParentAutowiredRequiredFalse.class);

        // when - then
        assertThat(refreshCntxAndGetBeanChild(cntx)).isNull();
    }

    @Test
    public void requiredFalseOneCandidate() {
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
    public void requiredFalseTwoCandidate() {
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
    public void qualifierByXmlDefintion() {
        // given
        var cntx = new ClassPathXmlApplicationContext("autowiring/qualifier.xml");
        cntx.getBean("beanParent");

        cntx.close();
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
