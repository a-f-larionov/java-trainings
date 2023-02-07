package demo002.beans;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThatException;

public class BeanNotFoundTest {

    @Test
    public void notFoundBeanName() {
        // given
        var configLocation = "container/minimal-requires.xml";
        var cntx = new ClassPathXmlApplicationContext(configLocation);

        // when
        ThrowingCallable callable = () -> cntx.getBean("notFoundBeanName");

        // then
        assertThatException()
                .isThrownBy(callable)
                .isInstanceOf(NoSuchBeanDefinitionException.class)
                .withMessage("No bean named 'notFoundBeanName' available")

                .withStackTraceContaining("context.support.AbstractApplicationContext.getBean")
                .withStackTraceContaining("beans.factory.support.AbstractBeanFactory.getBean")
                .withStackTraceContaining("beans.factory.support.AbstractBeanFactory.getMergedLocalBeanDefinition")
                .withStackTraceContaining("beans.factory.support.DefaultListableBeanFactory.getBeanDefinition");
        cntx.close();
    }
}
