package learn.parttwo;

import java.util.concurrent.RecursiveTask;

public class ForkJoinCounter extends RecursiveTask<Long> {
    private static final long SINGLE_THREAD_TASKS = 50;
    long[] numbers;
    int start;
    int end;

    public ForkJoinCounter(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinCounter(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if ((end - start) <= SINGLE_THREAD_TASKS) {
            return calculateNumbers();
        }

        ForkJoinCounter leftCounter = new ForkJoinCounter(numbers, start, start + (end - start) / 2);
        ForkJoinCounter rightCounter = new ForkJoinCounter(numbers, start + (end - start) / 2, end);
        rightCounter.fork();

        return leftCounter.compute() + rightCounter.join();
    }

    private long calculateNumbers() {
        long result = 0;

        for (int i = start; i < end; i++) {
            result += numbers[i];
        }

        return result;
    }
}
