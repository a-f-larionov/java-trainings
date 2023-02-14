package spring.autowiring;

import spring.autowiring.predefined_beans.BeanWithPredefined;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

public class PreDefinedBeansTest {

    @Test
    public void applicationContext() {
        // given
        var cnxt = new AnnotationConfigApplicationContext();
        cnxt.register(BeanWithPredefined.class);
        cnxt.refresh();

        // when
        var bean = cnxt.getBean("bean", BeanWithPredefined.class);

        // then
        assertThat(bean.getApplicationContext()).isSameAs(cnxt);
        assertThat(bean.getEnvironment()).isInstanceOf(Environment.class);
        assertThat(bean.getBeanFactory()).isInstanceOf(BeanFactory.class);
    }
}
