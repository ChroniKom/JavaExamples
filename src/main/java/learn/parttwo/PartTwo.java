package learn.parttwo;

import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.*;

// Streams API
// stream - is created for parallel compute of data, stream can operate with Collections and I/O Data
// Stream.stream() is single thread of processing of data
// Stream.parallelStream() is a multithreaded processing, it will take care automatically of the threads, and you dont need to worry about multithreading

// There is 2 types of stream operations
// 1. intermediate operations - this operations that does return Stream again: filter/sorted/limit etc...
// 2. terminal operations - this operations that actually run execution of stream and getting results for Type of data

// intermediate operations are processed differently, depending on which list of operations are there(see DIFFERENT BEHAVIOUR section here):
// 1. StateLess - by element through all operations
// 2. StateFull - by operation through all elements

public class PartTwo {
    public static void main(String[] args) {
        //Quiz
        quiz();
        System.out.println();
        mathQuiz();
        System.out.println();

        List<Dish> menu = Arrays.asList(
                new Dish("BEEF", false, 1500, DishType.MEET),
                new Dish("CHICKEN", false, 1000, DishType.MEET),
                new Dish("pizza", true, 2500, DishType.OTHER),
                new Dish("cesar", false, 1000, DishType.OTHER),
                new Dish("treska", true, 1500, DishType.FISH),
                new Dish("filet", true, 1500, DishType.FISH)
        );

        {
            Map<Integer, List<Dish>> mapByColories = menu.stream()
                    .collect(Collectors.groupingBy(Dish::getColories));
            System.out.println("Group by Colories: " + mapByColories);
        }

        {
            List<String> highColoriesNames = menu.stream()
                    .filter(dish -> dish.getColories() > 1000)
                    .sorted(Comparator.comparing(Dish::getColories))
                    .map(Dish::getName)
                    .limit(3)
                    .collect(Collectors.toList());

            System.out.println("Get 3 highes colories dishes: " + highColoriesNames);
        }

        // DIFFERENT BEHAVIOUR(for StateLess and StateFull intermediate operations)
        // Intermediate operations process differently(depends on which operations are here)
        // in below example 2 different behaviours:
        // StateFull - sort
        // StateLess - filter/map

        // 1. if to comment out sorted() method, it will take each "element" from "menu" and go through each "intermediate operation" with single element,
        //    once done, will take next element
        // 2. if to uncomment sorted() method, it will take each "operation" from "stream" and go through each "element" with single operation,
        //    once this operation are done, it will step to next operation and process remaining list(like here after filter, list is less for sorted operation) again.
        {
            List<String> debugHighColoriesNames = menu.stream()
                    .filter(dish -> {
                        System.out.println(Thread.currentThread().getId() + ": Filtering: " + dish.getName());
                        return dish.getColories() > 1000;
                    })
                    .sorted(Comparator.comparing(dish -> {
                        System.out.println(Thread.currentThread().getId() + ": Comparing: " + dish.getName());
                        return dish.getColories();
                    }))
                    .map(dish -> {
                        System.out.println(Thread.currentThread().getId() + ": Mapping: " + dish.getName());
                        return dish.getName();
                    })
                    .limit(3)
                    .collect(Collectors.toList());

            System.out.println("Get 3 highes colories dishes: " + debugHighColoriesNames);
        }

        // Fork/Join Java 7 API
        {
            long[] nums = LongStream.rangeClosed(0, 1000).toArray();
            System.out.println("sum: " + new ForkJoinPool().invoke(new ForkJoinCounter(nums)));
        }

        //Splitterator with Stream
        {
            long[] nums = LongStream.rangeClosed(0, 1000).toArray();

            Spliterator<Long> spliterator = new SplitteratorCounter(nums);
            System.out.println("sum: " + StreamSupport.stream(spliterator, true).reduce(new Counter(0), Counter::accumulate, Counter::combine).getCounter());
        }
    }

    static void mathQuiz() {
        //Print first 20, Fibonachi numbers 0, 1, 1, 2, 3, 5, 8...
        Stream.iterate(new int[]{0, 1}, (int[] num) -> new int[]{num[1], num[1] + num[0]})
                .limit(20).forEach(i -> System.out.print(i[0] + " "));
        System.out.println();

        //Pythagorian triples: a^2 + b^2 = c^2
        Stream<int[]> pythagorianTriples = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                                .mapToObj(b -> new int[] {a, b}))
                .filter(ints -> Math.sqrt(ints[0]*ints[0] + ints[1]*ints[1]) % 1 == 0);

        pythagorianTriples.forEach(ints -> System.out.println("a:" + ints[0] + " b:" + ints[1] + " = c:" + (int) Math.sqrt(ints[0]*ints[0] + ints[1]*ints[1])));
        System.out.println();

        //Pythagorian triples: a^2 + b^2 = c^2
        Stream<double[]> pythagorianTriplesDouble = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .mapToObj(b -> new double[] {a, b, Math.sqrt(a*a + b*b)}))
                .filter(nums -> nums[2] % 1 == 0);

        pythagorianTriplesDouble.forEach(nums -> System.out.println("a:" + nums[0] + " b:" + nums[1] + " = c:" + nums[2]));
        System.out.println();
    }

    static void quiz() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        quiz1(transactions);
        quiz2(transactions);
        quiz3(transactions);
        quiz4(transactions);
        quiz5(transactions);
        quiz6(transactions);
        quiz7(transactions);
        quiz8(transactions);
    }

    //Find all transactions in the year 2011 and sort them by value (small to high).
    static void quiz1(List<Transaction> transactions) {
        List<Transaction> oldTransactions = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparingInt(Transaction::getValue))
                .collect(Collectors.toList());

        System.out.println("quiz1: " + oldTransactions);
    }

    //What are all the unique cities where the traders work?
    static void quiz2(List<Transaction> transactions) {
        long count = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .distinct()
                .count();

        System.out.println("quiz2: " + count);
    }

    //3 Find all traders from Cambridge and sort them by name.
    static void quiz3(List<Transaction> transactions) {
        List<Trader> cambrideTraders = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());

        System.out.println("quiz3: " + cambrideTraders);
    }

    //4 Return a string of all traders’ names sorted alphabetically.
    static void quiz4(List<Transaction> transactions) {
        Optional<String> traders = transactions.stream()
                .map(Transaction::getTrader)
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .map(Trader::getName)
                .reduce((s, trader) -> s.concat(" ").concat(trader));

        System.out.println("quiz4: " + traders.orElse("empty"));
    }

    //5 Are any traders based in Milan?
    static void quiz5(List<Transaction> transactions) {
        Optional<Trader> milanTrader = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Milan"))
                .findAny();

        System.out.println("quiz5: " + milanTrader.isPresent());
    }

    //6 Print the values of all transactions from the traders living in Cambridge.
    static void quiz6(List<Transaction> transactions) {
        List<Integer> cambridgeValues = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .collect(Collectors.toList());

        System.out.println("quiz6: " + cambridgeValues);
    }

    //7 What’s the highest value of all the transactions?
    static void quiz7(List<Transaction> transactions) {
        Optional<Integer> highValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);

        System.out.println("quiz7: " + highValue.orElse(0));
    }

    //8 Find the transaction with the smallest value.
    static void quiz8(List<Transaction> transactions) {
        Optional<Transaction> smallestTransaction = transactions.stream()
                .reduce((transaction, transaction2) -> transaction.getValue() <= transaction2.getValue() ? transaction : transaction2);

        System.out.println("quiz8: " + smallestTransaction.get());
    }
}
