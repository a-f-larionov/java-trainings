package demo002.scopes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ScopesTest {

    @Test
    public void annotationProps() {
        var scopeAnnotation = BeanSingleton.class.getAnnotation(Scope.class);

        // then
        assertThat(scopeAnnotation.value()).isEqualTo(ConfigurableBeanFactory.SCOPE_SINGLETON);
        assertThat(scopeAnnotation.proxyMode()).isEqualTo(ScopedProxyMode.DEFAULT);
    }

    @Test
    public void singletonBeing() {
        // given
        var cntx = getScannedRefreshedCntx();

        // when
        var bf = cntx.getDefaultListableBeanFactory();
        var bd = cntx.getBeanDefinition("beanSingleton");

        // then
        assertBdIs(bd, true, false, false);

        assertTrue(List.of(bf.getSingletonNames()).contains("beanSingleton"));
        assertEquals(10, bf.getBeanDefinitionCount());
        assertEquals(16, bf.getSingletonCount());
    }

    @Test
    public void prototypeBeing() {
        // given
        var cntx = getScannedRefreshedCntx();

        // when
        var bf = cntx.getDefaultListableBeanFactory();
        var bd = cntx.getBeanDefinition("beanPrototype");

        // then
        assertBdIs(bd, false, true, false);

        assertFalse(List.of(bf.getSingletonNames()).contains("beanPrototype"));
        assertEquals(10, bf.getBeanDefinitionCount());
        assertEquals(16, bf.getSingletonCount());
    }

    @Test
    public void singletonMultiplyFactory() {
        // given
        var cntx = getScannedRefreshedCntx();

        // when
        var firstBean = cntx.getBean("beanSingleton", BeanSingleton.class);
        var secondBean = cntx.getBean("beanSingleton", BeanSingleton.class);

        // then
        assertSame(firstBean, secondBean);
    }

    @Test
    public void prototypeMultiplyFactory() {
        // given
        var cntx = getScannedRefreshedCntx();

        // when
        var firstBean = cntx.getBean("beanPrototype", BeanPrototype.class);
        var secondBean = cntx.getBean("beanPrototype", BeanPrototype.class);

        // then
        assertNotSame(firstBean, secondBean);
    }

    @Test
    public void singletonAutowired() {
        // given
        var cntx = getScannedRefreshedCntx();

        // when
        var parent = cntx.getBean(BeanParent.class);

        // then
        assertNotNull(parent.getFirstBeanSingleton());
        assertSame(parent.getFirstBeanSingleton(), parent.getSecondBeanSingleton());
    }

    @Test
    public void prototypeAutowired() {
        // given
        var cntx = getScannedRefreshedCntx();

        // when
        var parent = cntx.getBean(BeanParent.class);

        // then
        assertNotNull(parent.getFirstBeanPrototype());
        assertNotNull(parent.getSecondBeanPrototype());
        assertNotSame(parent.getFirstBeanPrototype(), parent.getSecondBeanPrototype());
    }

    @Test
    public void autowiredInsidePrototype() {
        // given
        var cntx = getScannedRefreshedCntx();

        // when
        var prototypeParent = cntx.getBean(BeanParentPrototype.class);

        // then
        assertNotNull(prototypeParent.getFirstBeanSingleton());
        assertNotNull(prototypeParent.getSecondBeanSingleton());
        assertNotNull(prototypeParent.getFirstBeanPrototype());
        assertNotNull(prototypeParent.getSecondBeanPrototype());

        assertSame(prototypeParent.getFirstBeanSingleton(), prototypeParent.getFirstBeanSingleton());
        assertNotSame(prototypeParent.getFirstBeanPrototype(), prototypeParent.getSecondBeanPrototype());
    }

    private AnnotationConfigApplicationContext getScannedRefreshedCntx() {
        var cntx = new AnnotationConfigApplicationContext();
        cntx.scan(getScopesNamespace());
        cntx.refresh();
        return cntx;
    }

    private String getScopesNamespace() {
        return this.getClass().getPackageName();
    }

    private void assertBdIs(BeanDefinition bd, boolean isSingleton, boolean isPrototype, boolean isLazyInit) {
        if (isSingleton) {
            assertThat(bd.isSingleton()).isTrue();
        } else {
            assertThat(bd.isSingleton()).isFalse();
        }
        if (isPrototype) {
            assertThat(bd.isPrototype()).isTrue();
        } else {
            assertThat(bd.isPrototype()).isFalse();
        }
        if (isLazyInit) {
            assertThat(bd.isLazyInit()).isTrue();
        } else {
            assertThat(bd.isLazyInit()).isFalse();
        }
    }
}
