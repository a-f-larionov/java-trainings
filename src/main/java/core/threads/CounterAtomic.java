package core.threads;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterAtomic implements Counter {

    private final int howManyTimes;
    private final AtomicInteger value = new AtomicInteger();

    public CounterAtomic(int howManyTimes) {
        this.howManyTimes = howManyTimes;
    }

    public int incrementManyTimes() {
        for (int i = 0; i < howManyTimes; i++) {
            value.incrementAndGet() ;
        }
        return getValue();
    }

    public int getValue() {
        return value.get();
    }
}
