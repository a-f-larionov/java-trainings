package demo002.container;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionStoreException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThatException;

public class InstantiatingContainer {

    @Test
    public void classPathXmlEmptyFile() {
        // given
        var configName = "container/empty-file.xml";

        // when
        ThrowingCallable callable = () -> new ClassPathXmlApplicationContext(configName);

        // then
        assertThatException()
                .isThrownBy(callable)
                .isInstanceOf(XmlBeanDefinitionStoreException.class)
                .withMessage("Line 1 in XML document from class path resource [container/empty-file.xml] is invalid");
    }

    @Test
    public void classPathXmlRootTagOnly() {
        // given
        var configName = "container/root-tag-only.xml";

        // when
        ThrowingCallable callable = () -> new ClassPathXmlApplicationContext(configName);

        // then
        assertThatException()
                .isThrownBy(callable)
                .isInstanceOf(XmlBeanDefinitionStoreException.class)
                .withMessage("Line 2 in XML document from class path resource [container/root-tag-only.xml] is invalid");
    }

    @Test
    public void classPathXmlRootWithAttrsOnly() {
        // given
        var configName = "container/root-with-attrs-only.xml";

        // when
        ThrowingCallable callable = () -> new ClassPathXmlApplicationContext(configName);

        // then
        assertThatException()
                .isThrownBy(callable)
                .isInstanceOf(BeanDefinitionStoreException.class)
                .withMessage("IOException parsing XML document from class path resource [container/root-with-attrs-only.xml]");
    }
}
