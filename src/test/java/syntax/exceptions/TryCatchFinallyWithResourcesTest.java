package syntax.exceptions;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
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

    @Test
    public void catchBeforeFinally() {
        boolean catchDone = false;
        boolean finallyDone = false;

        try {

            throw new RuntimeException("TryBlock");

        } catch (Throwable t) {

            catchDone = true;

            assertThat(catchDone).isTrue();
            assertThat(finallyDone).isFalse();

        } finally {

            finallyDone = true;

            assertThat(catchDone).isTrue();
            assertThat(finallyDone).isTrue();
        }

        assertThat(catchDone).isTrue();
        assertThat(finallyDone).isTrue();
    }


    static class MyRuntimeException extends RuntimeException {

        public MyRuntimeException(String deep_try_block) {
            super(deep_try_block);
        }

        public MyRuntimeException(String deep_catch_block, Throwable t) {
            super(deep_catch_block, t);
        }
    }

    @Test
    public void catchThrows() {

        var sequense = new LinkedList<String>();

        try {
            sequense.add("top try");
            try {
                sequense.add("deep try");

                throw new MyRuntimeException("Deep Try Block");

            } catch (MyRuntimeException t) {
                sequense.add("deep catch");

                assertThat(t).hasMessage("Deep Try Block").hasNoCause();

                sequense.add("deep catch after assert");

                // this exception
                throw new MyRuntimeException("Deep Catch Block", t);

            } finally {
                sequense.add("deep finally");

                throw new MyRuntimeException("Deep Finally Block");
            }
        } catch (MyRuntimeException t) {
            sequense.add("top catch");

            assertThat(t).hasMessage("Deep Finally Block").hasNoCause();

            sequense.add("top catch after assert");
        }

        assertThat(sequense).isEqualTo(List.of(
                "top try",
                "deep try",

                "deep catch",
                "deep catch after assert",
                "deep finally",

                "top catch",
                "top catch after assert"
        ));
    }
}
