package patterns.singleton;

import lombok.SneakyThrows;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.BrokenBarrierException;

import static java.lang.reflect.Modifier.*;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SingletonTest {


    static class Locker {

        private volatile boolean locked = false;
        private final Object mutex = new Object();


        @SneakyThrows
        public void lock() {
            synchronized (mutex) {
                while (!locked) {
                   // Thread.sleep(10);
                }
            }
        }

        public void waitUnlock() {
            synchronized (mutex) {
                locked = false;
            }
        }

        public void unlock() {
            locked = true;
        }
    }

    static class ThreadUnSafeSingleton {

        private static ThreadUnSafeSingleton instance;

        private ThreadUnSafeSingleton() {

        }


        public static ThreadUnSafeSingleton getInstance() {
            return getInstance(null);
        }

        public static ThreadUnSafeSingleton getInstance(Locker locker) {
            if (instance == null) {
                if (locker != null) {
                    locker.waitUnlock();
                }
                instance = new ThreadUnSafeSingleton();
            }
            return instance;
        }


    }

    static class ThreadSafeSingleton {

        private static volatile ThreadSafeSingleton instance;

        private ThreadSafeSingleton() {
        }

        public static ThreadSafeSingleton getInstance() {
            if (ThreadSafeSingleton.instance == null) {
                createInstanceSynchronized();
            }
            return instance;
        }

        private static synchronized void createInstanceSynchronized() {
            if (instance == null) {
                instance = new ThreadSafeSingleton();
            }
        }
    }

    @Test
    public void examineClass() throws NoSuchFieldException, NoSuchMethodException {
        // given
        var singletonClass = ThreadSafeSingleton.class;

        // when

        Field instance = singletonClass.getDeclaredField("instance");
        Method getInstance = singletonClass.getDeclaredMethod("getInstance");
        Constructor<ThreadSafeSingleton> constructor = singletonClass.getDeclaredConstructor();

        var instanceMods = instance.getModifiers();
        var getInstanceMods = getInstance.getModifiers();
        var constructorMods = constructor.getModifiers();


        //  then

        /* constructor private */
        assertTrue(isPrivate(constructorMods));

        /* private static instance */
        assertTrue(isPrivate(instanceMods));
        assertTrue(isStatic(instanceMods));

        /* public static getInstance */
        assertTrue(isPublic(getInstanceMods));
        assertTrue(isStatic(getInstanceMods));
    }

    @Test
    public void noRaceCandition() {
        // given
        var singleton1 = ThreadSafeSingleton.getInstance();
        var singleton2 = ThreadSafeSingleton.getInstance();

        // when - then
        assertSame(singleton1, singleton2);
    }


    private void sneaky(ThrowableAssert.ThrowingCallable r) {
        try {
            r.call();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void threadUnsafeRaceCandtitionTest() throws BrokenBarrierException, InterruptedException {

        var locker = new Locker();
        locker.lock();

        Thread t1 = new Thread(() -> {
            ThreadUnSafeSingleton.getInstance();
            locker.unlock();
        });
        Thread t2 = new Thread(() -> {
            ThreadUnSafeSingleton.getInstance(locker);
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
