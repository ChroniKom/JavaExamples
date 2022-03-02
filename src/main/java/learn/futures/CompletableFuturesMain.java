package learn.futures;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class CompletableFuturesMain {
    private static final ExecutorService singleThreadPool = Executors.newFixedThreadPool(1);
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    private static final List<String> STRING_LIST = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "k", "l", "1", "2", "3", "4", "5", "6", "7", "8", "9");

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        System.out.println("Task1(Simple): " + simpleFuture().get(5, TimeUnit.SECONDS));

        Future<String> future2 = singleThreadPool.submit(() -> "Completed by ExecutedService");
        System.out.println("Task2(Simple): " + future2.get());

        long start = System.currentTimeMillis();
        Future<String> future3 = singleThreadPool.submit(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ignore) {}

            return "b";
        });
        System.out.println("Task3(Asynch): sum - " + worker1() + future3.get() + " (finished in: " + (System.currentTimeMillis() - start) + "ms)");

        start = System.currentTimeMillis();
        System.out.println("Task3(Stream): result - " + complicatedFuture() + " (finished in: " + (System.currentTimeMillis() - start) + "ms)");

        singleThreadPool.shutdown();
        threadPool.shutdown();
    }

    private static Future<String> simpleFuture() {
        CompletableFuture<String> future = new CompletableFuture<>();

        new Thread(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ignore) {}

            future.complete("completable Completed");
        }).start();

        return future;
    }

    private static String complicatedFuture() {
        List<CompletableFuture<String>> futures = STRING_LIST.stream()
                .map(CompletableFuturesMain::submitFuture)
                .collect(Collectors.toList());

        StringBuilder builder = new StringBuilder();
        futures.forEach(stringFuture -> builder.append(stringFuture.join()));

        return builder.toString();
    }

    private static String worker1() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException ignore) {}

        return "a";
    }

    private static CompletableFuture<String> submitFuture(String string) {
        CompletableFuture<String> future = new CompletableFuture<>();
        threadPool.submit(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ignore) {}
            future.complete(string);
        });

        return future;
    }
}
