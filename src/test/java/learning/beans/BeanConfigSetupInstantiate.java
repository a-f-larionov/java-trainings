package learning.beans;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThatException;

public class BeanConfigSetupInstantiate {

    @Test
    public void noUniqueBeanName() {
        // given
        var configLocation = "beans/no-unique-bean-name.xml";

        // when
        ThrowingCallable runnable = () -> new ClassPathXmlApplicationContext(configLocation);

        // then
        assertThatException()
                .isThrownBy(runnable)
                .isInstanceOf(BeanDefinitionStoreException.class)
                .withMessage("Configuration problem: Bean name 'beanName1' is already used in this <beans> element\n" +
                        "Offending resource: class path resource [" + configLocation + "]" +
                        "\n");
    }

    @Test
    public void noBeanClassSpecified() {
        // given
        var configName = "beans/no-bean-class-specified.xml";

        // when
        ThrowingCallable runnable = () -> new ClassPathXmlApplicationContext(configName);

        // then
        assertThatException()
                .isThrownBy(runnable)
                .isInstanceOf(BeanCreationException.class)
                .withMessage("Error creating bean with name 'beanName1' defined in class path resource [beans/no-bean-class-specified.xml]: " +
                        "No bean class specified on bean definition");
    }
}
