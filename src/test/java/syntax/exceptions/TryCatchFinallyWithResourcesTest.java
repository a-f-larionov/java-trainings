package syntax.exceptions;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class TryCatchFinallyWithResourcesTest {

    @Test
    public void withResourcesOnly() throws Throwable {
        // given
        var autoCloseable = mock(AutoCloseable.class);

        // when
        try {
            try (autoCloseable) {
                throw new RuntimeException("My run time exception");
            }
        } catch (Throwable ignore) {
        }

        // then
        verify(autoCloseable, times(1)).close();
    }

    @Test
    public void minimalTry() throws Throwable {
        // given
        var autoCloseable = mock(AutoCloseable.class);

        // variant 1
        try (autoCloseable) {
            // do something
        }

        // variant 2
        try {
            // do something
        } catch (Throwable ignore) {
            // do something
        }
    }

    @Test
    public void checkedThrowable() throws IOException {
        ThrowingCallable r = () -> {
            throw new IOException("some text");
        };
        assertThatException()
                .isThrownBy(r)
                .isInstanceOf(Exception.class);
    }

    @Test
    public void uncheckedThrowable() {
        Runnable runnable = () -> {
            throw new RuntimeException("Какой то текст");
        };
        ThrowingCallable throwingCallable = () -> runnable.run();

        assertThatException()
                .isThrownBy(throwingCallable)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void errorIsUnchecked() {
        Runnable runnable = () -> {
            throw new Error("some text");
        };

        ThrowingCallable throwing = runnable::run;

        assertThatThrownBy(throwing)
                .isInstanceOf(Error.class);
    }

    @Test
    public void popularThrowable() {

        var throwables = new Throwable[]{
                // top - 2
                new NullPointerException(),
                new RuntimeException(),

                // popular errors
                new OutOfMemoryError(),
                new StackOverflowError(),

                // popular runtime
                new IndexOutOfBoundsException(),
                new ArrayIndexOutOfBoundsException(),
                new IllegalArgumentException(),

                // popular checked
                new IOException(),
                new InterruptedException(),
                new FileNotFoundException()
        };
    }
}
