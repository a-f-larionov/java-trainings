package core.threads;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CompareSimpleSynchAndAtomicTest {

    private final int threadsCount = 10;
    private final int howManyTimes = 1_00_000;
    private final int incSumm = threadsCount * howManyTimes;

    private final Counter counterSimple = new CounterSimple(howManyTimes);
    private final Counter counterSynced = new CounterSynchronized(howManyTimes);
    private final Counter counterAtomic = new CounterAtomic(howManyTimes);

    @Test
    public void simpleInc() throws InterruptedException {
        // given
        var threads = createAndPrepareThreads(counterSimple);

        // when
        var startMls = System.currentTimeMillis();
        startAndJoinThreads(threads);

        // then
        logInfo("simple", startMls);
        assertThat(counterSimple.getValue()).isLessThan(incSumm);
    }

    @Test
    public void synchronizedInc() throws InterruptedException {
        // given
        var threads = createAndPrepareThreads(counterSynced);

        // when
        var startMls = System.currentTimeMillis();
        startAndJoinThreads(threads);

        // then
        logInfo("sync. ", startMls);
        assertThat(counterSynced.getValue()).isEqualTo(incSumm);
    }

    @Test
    public void atomicInc() throws InterruptedException {
        // given
        var threads = createAndPrepareThreads(counterAtomic);

        // when
        var startMls = System.currentTimeMillis();
        startAndJoinThreads(threads);

        // then
        logInfo("atomic", startMls);
        assertThat(counterAtomic.getValue()).isEqualTo(incSumm);
    }

    private Thread[] createAndPrepareThreads(Counter counter) {
        var threads = new Thread[threadsCount];
        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new Thread(counter::incrementManyTimes);
        }
        return threads;
    }

    private void startAndJoinThreads(Thread[] threads) throws InterruptedException {
        for (int i = 0; i < threadsCount; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threadsCount; i++) {
            threads[i].join();
        }
    }

    private void logInfo(String title, long startMls) {
        var mls = (System.currentTimeMillis() - startMls);
        if (mls == 0) mls = 1;
        System.out.printf(title +
                "\t threads: %5d" +
                "\t times: %8d" +
                "\t mls: %5d" +
                "\t per sec.: %,8d\n", threadsCount, howManyTimes, mls, ((incSumm) / mls));
    }
}
