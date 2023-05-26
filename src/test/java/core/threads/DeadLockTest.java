package core.threads;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class DeadLockTest {

    @Test
    @Disabled
    void deadLock() throws InterruptedException {

        var resource1 = new Object();
        var resource2 = new Object();

        var thread1 = new Thread(() -> {
            System.out.println("1: run");
            synchronized (resource1) {
                sleep();
                synchronized (resource2) {

                }
            }
        });

        var thread2 = new Thread(() -> {
            System.out.println("2: run");
            synchronized (resource2) {
                sleep();
                synchronized (resource1) {
                    System.out.println("2: block 2 inside");
                }
            }
        });


        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }

    private void sleep() {
        try {
            System.out.println(Thread.currentThread().getId() + " sleep start");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getId() + " sleep finished");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}