package core.threads;

public class CounterSynchronized implements Counter {

    private final int howManyTimes;
    private int value = 0;

    public CounterSynchronized(int howManyTimes) {
        this.howManyTimes = howManyTimes;
    }

    public synchronized int incrementManyTimes() {
        for (int i = 0; i < howManyTimes; i++) {
            value++;
        }
        return getValue();
    }

    public int getValue() {
        return value;
    }
}