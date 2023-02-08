package learning.autowiring;

import learning.autowiring.simple.BeanChild;
import learning.autowiring.simple.BeanParent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.support.GenericApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ByBeanDefinitionTest {

    private GenericApplicationContext cntx;
    private GenericBeanDefinition beanParent;

    @Test
    public void justManualCreateClass() {
        // given - when
        var masterBean = new BeanParent();

        // then
        assertDependsBeansWired(masterBean, false, false, false, false, false);
    }

    @Test
    public void autowireDefault() {
        // given
        createCntxWithParentAndChild();

        // when - then
        assertDependsBeansWired(cntx, false, false, false, false, false);
    }

    @Test
    public void autowireModeConstructor() {
        // given
        createCntxWithParentAndChild();
        beanParent.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR);

        // when - then
        assertDependsBeansWired(cntx, false, true, false, false, false);
    }

    @Test
    public void autowireModeByType() {
        // given
        createCntxWithParentAndChild();
        beanParent.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE);

        // when - then
        assertDependsBeansWired(cntx, false, false, true, true, true);
    }

    @Test
    public void autowireModeByName() {
        // given
        createCntxWithParentAndChild();
        beanParent.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_NAME);

        var beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(BeanChild.class);
        cntx.registerBeanDefinition("beanByName", beanDefinition);

        // when - then
        assertDependsBeansWired(cntx, false, false, false, false, true);
    }

    @Test
    public void autowireByProperty() {
        // given
        createCntxWithParentAndChild();
        beanParent.getPropertyValues().add(
                "beanByPropertyValues",
                new RuntimeBeanReference("beanChild"));

        // when - then
        assertDependsBeansWired(cntx, false, false, false, true, false);
    }

    @Test
    public void excludeAutowireCandidateFalse() {
        // given
        createCntxWithParentAndChild();
        beanParent.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE);

        var beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(BeanChild.class);
        beanDefinition.setAutowireCandidate(false);
        cntx.registerBeanDefinition("beanChild2", beanDefinition);

        // when - then
        cntx.refresh();

        assertThat(cntx.getBean("beanParent", BeanParent.class)
                .getBeanByType())
                .isInstanceOf(BeanChild.class);
    }

    @Test
    public void autowireByAnnotationAutowire() {
        // given
        createCntxWithParentAndChild();
        var bpp = new AutowiredAnnotationBeanPostProcessor();
        bpp.setBeanFactory(cntx.getBeanFactory());
        cntx.getBeanFactory().addBeanPostProcessor(bpp);

        // when - then
        assertDependsBeansWired(cntx, true, false, false, false, false);
    }


    private GenericBeanDefinition addBeanParent(GenericApplicationContext cntx) {
        var bd = new GenericBeanDefinition();
        bd.setBeanClass(BeanParent.class);
        cntx.registerBeanDefinition("beanParent", bd);
        return bd;
    }

    private void addBeanChild(GenericApplicationContext cntx) {
        var bd = new GenericBeanDefinition();
        bd.setBeanClass(BeanChild.class);
        cntx.registerBeanDefinition("beanChild", bd);
    }

    private void assertDependsBeansWired(GenericApplicationContext cntx,
                                         boolean byAnnotation,
                                         boolean byConstructor, boolean byType, boolean byPropertyValues, boolean byName) {
        cntx.refresh();
        assertDependsBeansWired(cntx.getBean("beanParent", BeanParent.class),
                byAnnotation, byConstructor, byType, byPropertyValues, byName
        );
    }

    private void assertDependsBeansWired(BeanParent beanParent,
                                         boolean byAnnotation,
                                         boolean byConstructor, boolean byType, boolean byPropertyValues, boolean byName) {
        if (byAnnotation) {
            assertThat(beanParent.getBeanByAnnotation()).isInstanceOf(BeanChild.class);
        } else {
            assertThat(beanParent.getBeanByAnnotation()).isNull();
        }
        if (byName) {
            assertThat(beanParent.getBeanByName()).isInstanceOf(BeanChild.class);
        } else {
            assertThat(beanParent.getBeanByName()).isNull();
        }
        if (byConstructor) {
            assertThat(beanParent.getBeanByConstructor()).isInstanceOf(BeanChild.class);
        } else {
            assertThat(beanParent.getBeanByConstructor()).isNull();
        }
        if (byType) {
            assertThat(beanParent.getBeanByType()).isInstanceOf(BeanChild.class);
        } else {
            assertThat(beanParent.getBeanByType()).isNull();
        }
        if (byPropertyValues) {
            assertThat(beanParent.getBeanByPropertyValues()).isInstanceOf(BeanChild.class);
        } else {
            assertThat(beanParent.getBeanByPropertyValues()).isNull();
        }
    }

    private void createCntxWithParentAndChild() {
        cntx = new GenericApplicationContext();
        beanParent = addBeanParent(cntx);
        addBeanChild(cntx);
    }
}
