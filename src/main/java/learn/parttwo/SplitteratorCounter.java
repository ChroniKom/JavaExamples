package learn.parttwo;

import java.util.Spliterator;
import java.util.function.Consumer;

public class SplitteratorCounter implements Spliterator<Long> {
    long[] numbers;
    int start;
    int end;

    public SplitteratorCounter(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private SplitteratorCounter(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Long> consumer) {
        return this.end - this.start < 63;
    }

    @Override
    public Spliterator<Long> trySplit() {
        int length = end - start;
        if (length/2 < 63) {
            return null;
        }
        Spliterator<Long> leftPart = new SplitteratorCounter(numbers, this.start, this.start + length/2);
        this.start = this.start + length/2;

        return leftPart;
    }

    @Override
    public long estimateSize() {
        return this.end - this.start;
    }

    @Override
    public int characteristics() {
        return CONCURRENT + DISTINCT + IMMUTABLE + SIZED;
    }
}
