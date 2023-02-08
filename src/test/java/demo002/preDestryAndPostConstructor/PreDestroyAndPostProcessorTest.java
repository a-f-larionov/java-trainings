package demo002.preDestryAndPostConstructor;

import demo002.autowiring.preDestroyAndPostConstructor.BeanWithCallbacks;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class PreDestroyAndPostProcessorTest {

    @Test
    public void postConstructor() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.scan(BeanWithCallbacks.class.getPackageName());
        cntx.refresh();

        // when
        BeanWithCallbacks bean = cntx.getBean("beanWithCallbacks", BeanWithCallbacks.class);

        // then
        assertThat(bean.isPostConstructCalled()).isTrue();
        cntx.close();
    }

    @Test
    public void preDestroyBeforeClose() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.scan(BeanWithCallbacks.class.getPackageName());
        cntx.refresh();

        // when
        BeanWithCallbacks bean = cntx.getBean("beanWithCallbacks", BeanWithCallbacks.class);

        // then
        assertThat(bean.isPreDestroyCalled()).isFalse();
        cntx.close();
    }

    @Test
    public void preDestroyAfterClose() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.scan(BeanWithCallbacks.class.getPackageName());
        cntx.refresh();

        // when
        BeanWithCallbacks bean = cntx.getBean("beanWithCallbacks", BeanWithCallbacks.class);
        cntx.close();

        // then
        assertThat(bean.isPreDestroyCalled()).isTrue();
    }
}
