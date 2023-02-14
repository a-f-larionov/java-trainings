package spring.customizin_the_nature_of_bean;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("resource")
public class InitializingBeanAndDisposibleTest {

    @Test
    public void initializingBean() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(BeanWithCallbacksInterfaces.class);
        cntx.refresh();
        // when
        var bean = cntx.getBean(BeanWithCallbacksInterfaces.class);

        // then
        assertTrue(bean.isAfterPropertiesSetCalled());
    }

    @Test
    public void disposibleBeforeCloseCntx() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(BeanWithCallbacksInterfaces.class);
        cntx.refresh();

        // when
        var bean = cntx.getBean(BeanWithCallbacksInterfaces.class);

        // then
        assertFalse(bean.isDestroyCalled());
    }

    @Test
    public void disposibleAfterCloseCntx() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(BeanWithCallbacksInterfaces.class);
        cntx.refresh();

        // when
        var bean = cntx.getBean(BeanWithCallbacksInterfaces.class);
        cntx.close();

        // then
        assertTrue(bean.isDestroyCalled());
    }
}
