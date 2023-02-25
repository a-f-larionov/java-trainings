package core.threads;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreadsTest {

    @Test
    public void everyThreadHasUniqueTid() throws InterruptedException {
        // given
        var tids = new java.util.concurrent.CopyOnWriteArrayList<Long>();
        Runnable runnable = () -> tids.add(Thread.currentThread().getId());
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        // when
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        Thread.activeCount();
        // then
        assertThat(tids)
                .hasSize(2)
                .doesNotContain(Thread.currentThread().getId());
        assertThat(tids.get(0))
                .isNotEqualTo(tids.get(1));
    }

    @Test
    public void everyThreadHasUniqueName() throws InterruptedException {
        // given
        final List<String> names = new java.util.concurrent.CopyOnWriteArrayList<>();
        Runnable r = () -> names.add(Thread.currentThread().getName());
        var t1 = new Thread(r);
        var t2 = new Thread(r);

        // when
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        // then
        assertThat(names)
                .hasSize(2);
        assertThat(names.get(0))
                .isNotEqualTo(names.get(1));
    }

}
