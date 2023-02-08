package learning.container;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.assertj.core.api.ThrowableAssertAlternative;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.xml.XmlBeanDefinitionStoreException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXParseException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatException;

public class InstantiatingContainer {

    @Test
    public void classPathXmlEmptyFile() {
        // given
        var configLocation = "container/empty-file.xml";

        // when
        ThrowingCallable callable = () -> new ClassPathXmlApplicationContext(configLocation);

        // then
        var toAssert = assertThatException()
                .isThrownBy(callable)
                .isInstanceOf(XmlBeanDefinitionStoreException.class)
                .withMessage("Line 1 in XML document from class path resource [" + configLocation + "] is invalid")

                .havingCause()
                .isInstanceOf(SAXParseException.class)
                .withMessage("Premature end of file.");

        assertInitToDoLoadBeanDefinitionStackTrace(toAssert);
    }

    @Test
    public void classPathXmlRootTagOnly() {
        // given
        var configLocation = "container/root-tag-only.xml";

        // when
        ThrowingCallable callable = () -> new ClassPathXmlApplicationContext(configLocation);

        // then
        var toAssert = assertThatException()
                .isThrownBy(callable)
                .isInstanceOf(XmlBeanDefinitionStoreException.class)
                .withMessage("Line 2 in XML document from class path resource [" + configLocation + "] is invalid")

                .havingCause()
                .isInstanceOf(SAXParseException.class)
                .withMessage("cvc-elt.1.a: Cannot find the declaration of element 'beans'.");
        assertInitToDoLoadBeanDefinitionStackTrace(toAssert);
    }

    @Test
    public void classPathXmlRootWithAttrsOnly() {
        // given
        var configLocation = "container/root-tag-with-attrs-only.xml";

        // when
        ThrowingCallable callable = () -> new ClassPathXmlApplicationContext(configLocation);

        // then
        var toAssert = assertThatException()
                .isThrownBy(callable)
                .isInstanceOf(XmlBeanDefinitionStoreException.class)
                .withMessage("Line 2 in XML document from class path resource [" + configLocation + "] is invalid")

                .havingCause()
                .isInstanceOf(SAXParseException.class)
                .withMessage("cvc-elt.1.a: Cannot find the declaration of element 'beans'.");

        assertInitToDoLoadBeanDefinitionStackTrace(toAssert);
    }

    @Test
    public void classPathXmlMinimalRequires() {
        // given
        var configLocation = "container/minimal-requires.xml";

        // when
        ThrowingCallable callable = () -> new ClassPathXmlApplicationContext(configLocation);

        // then
        assertThatCode(callable).doesNotThrowAnyException();
    }

    private void assertInitToDoLoadBeanDefinitionStackTrace(ThrowableAssertAlternative<?> throwableAssertAlternative) {
        throwableAssertAlternative.withStackTraceContaining(".context.support.ClassPathXmlApplicationContext.<init>")
                .withStackTraceContaining(".context.support.AbstractApplicationContext.refresh")
                .withStackTraceContaining(".context.support.AbstractApplicationContext.obtainFreshBeanFactory")
                .withStackTraceContaining(".context.support.AbstractRefreshableApplicationContext.refreshBeanFactory")
                .withStackTraceContaining(".context.support.AbstractXmlApplicationContext.loadBeanDefinitions")
                .withStackTraceContaining(".beans.factory.xml.XmlBeanDefinitionReader.loadBeanDefinitions")
                .withStackTraceContaining(".beans.factory.xml.XmlBeanDefinitionReader.doLoadBeanDefinitions")
        ;
    }
}
