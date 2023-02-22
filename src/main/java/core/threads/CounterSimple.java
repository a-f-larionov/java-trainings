package core.threads;

public class CounterSimple implements Counter {

    private final int howManyTimes;
    private int value = 0;

    public CounterSimple(int howManyTimes) {
        this.howManyTimes = howManyTimes;
    }

    public int incrementManyTimes() {
        for (int i = 0; i < howManyTimes; i++) {
            value++;
        }
        return getValue();
    }

    public int getValue() {
        return value;
    }
}