package spring.customizin_the_nature_of_bean;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class PreDestroyAndPostProcessorTest {

    @Test
    public void postConstructor() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.scan(BeanWithCallbacksAnnotated.class.getPackageName());
        cntx.refresh();

        // when
        BeanWithCallbacksAnnotated bean = cntx.getBean(BeanWithCallbacksAnnotated.class);

        // then
        assertThat(bean.isPostConstructCalled()).isTrue();
        cntx.close();
    }

    @Test
    public void preDestroyBeforeClose() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.scan(BeanWithCallbacksAnnotated.class.getPackageName());
        cntx.refresh();

        // when
        BeanWithCallbacksAnnotated bean = cntx.getBean(BeanWithCallbacksAnnotated.class);

        // then
        assertThat(bean.isPreDestroyCalled()).isFalse();
        cntx.close();
    }

    @Test
    public void preDestroyAfterClose() {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        cntx.scan(BeanWithCallbacksAnnotated.class.getPackageName());
        cntx.refresh();

        // when
        BeanWithCallbacksAnnotated bean = cntx.getBean(BeanWithCallbacksAnnotated.class);
        cntx.close();

        // then
        assertThat(bean.isPreDestroyCalled()).isTrue();
    }
}
