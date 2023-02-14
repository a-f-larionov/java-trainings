package try_catch_finally;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class TryCatchFinallyTest {

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

        // variant 3
        try {
            // do something
        } finally {
            // do something
        }
    }

    @Test
    public void checkedThrowable() throws IOException {
        throw new IOException("some text");
        // expected `throws`
    }

    @Test
    public void uncheckedThrowable() {
        throw new RuntimeException("Какой то текст");
        // expected nope `throws`
    }

    @Test
    public void errorIsUnchecked() {
        throw new Error("some text");
    }

    @Test
    public void popularThrowables() {
        var strs = new String[]{"a", "b"};
        var objs = new Object[]{new Object()};

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
