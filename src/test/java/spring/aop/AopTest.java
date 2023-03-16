package spring.aop;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class AopTest {

    @Test
    public void test1() {
        // given
        var cntx = new AnnotationConfigApplicationContext(MyConfigurtion.class.getPackageName());

        ServiceA serviceA = cntx.getBean(ServiceA.class);
        ServiceB serviceB = cntx.getBean(ServiceB.class);

        // when
        serviceA.serviceB.methodB();

        // then
        assertThat(serviceB.isMethodBCalled()).isTrue();
        assertThat(serviceB.isAspectCalled()).isTrue();
    }
}
