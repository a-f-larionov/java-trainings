package core.threads;

import org.junit.jupiter.api.Test;

import javax.swing.plaf.synth.SynthTableHeaderUI;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreadsTest {

    @Test
    public void uniqueTid() throws InterruptedException {
        // given
        var tids = new ArrayList<Long>();
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

}
