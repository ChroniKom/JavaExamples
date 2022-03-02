package learn.parttwo;

public class Counter {
    private long counter = 0;

    public Counter(long counter) {
        this.counter = counter;
    }

    public Counter accumulate(long num) {
        return new Counter(num);
    }

    public Counter combine(Counter counter) {
        return new Counter(this.counter + counter.counter);
    }

    public long getCounter() {
        return counter;
    }
}
