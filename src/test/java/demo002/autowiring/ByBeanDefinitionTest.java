package demo002.autowiring;

import demo002.autowiring.simple.BeanChild;
import demo002.autowiring.simple.BeanParent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.support.GenericApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ByBeanDefinitionTest {

    private GenericApplicationContext cntx;
    private GenericBeanDefinition parentBean;

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
        parentBean.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR);

        // when - then
        assertDependsBeansWired(cntx, false, true, false, false, false);
    }

    @Test
    public void autowireModeByType() {
        // given
        createCntxWithParentAndChild();
        parentBean.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE);

        // when - then
        assertDependsBeansWired(cntx, false, false, true, true, true);
    }

    @Test
    public void autowireModeByName() {
        // given
        createCntxWithParentAndChild();
        parentBean.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_NAME);

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
        parentBean.getPropertyValues().add(
                "beanByPropertyValues",
                new RuntimeBeanReference("childBean"));

        // when - then
        assertDependsBeansWired(cntx, false, false, false, true, false);
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


    private GenericBeanDefinition addParentBean(GenericApplicationContext cntx) {
        var bd = new GenericBeanDefinition();
        bd.setBeanClass(BeanParent.class);
        cntx.registerBeanDefinition("parentBean", bd);
        return bd;
    }

    private void addChildBean(GenericApplicationContext cntx) {
        var bd = new GenericBeanDefinition();
        bd.setBeanClass(BeanChild.class);
        cntx.registerBeanDefinition("childBean", bd);
    }

    private void assertDependsBeansWired(GenericApplicationContext cntx,
                                         boolean byAnnotation,
                                         boolean byConstructor, boolean byType, boolean byPropertyValues, boolean byName) {
        cntx.refresh();
        assertDependsBeansWired(cntx.getBean("parentBean", BeanParent.class),
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
        parentBean = addParentBean(cntx);
        addChildBean(cntx);
    }
}
