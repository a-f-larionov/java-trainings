package spring.dependencies;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Circular {

    @Test
    public void byConstructors() {
        // given - when
        ThrowingCallable runnable = () -> new ClassPathXmlApplicationContext(
                "dependencies/circular/by-constructors.xml");
        // then
        assertThatThrownBy(runnable).isInstanceOf(org.springframework
                .beans.factory.BeanCreationException.class);
    }

    @Test
    public void bySetters() {
        // given - when
        ThrowingCallable runnable = () -> new ClassPathXmlApplicationContext(
                "dependencies/circular/by-setters.xml");

        // then
        assertThatCode(runnable).doesNotThrowAnyException();
    }

    @Test
    public void selfByConstructor() {
        // given - when
        ThrowingCallable runnable = () -> new ClassPathXmlApplicationContext(
                "dependencies/circular/self-by-constructor.xml");
        // then
        assertThatThrownBy(runnable).hasCauseInstanceOf(BeanCurrentlyInCreationException.class);

    }

    @Test
    public void selfBySetter() {
        // given - when
        ThrowingCallable runnable = () -> new ClassPathXmlApplicationContext(
                "dependencies/circular/self-by-setter.xml");
        // then
        assertThatCode(runnable).doesNotThrowAnyException();
    }

}
