package learn.partone;

import learn.function.BiFunctional;
import learn.util.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// Types of passing the code as a Paramter
// 1 - Behaviour Parametrization
// 2 - Anonymous Classes
// 3 - Lambda's
// 4 - Method Reference(Constructor Reference)

@SuppressWarnings("all")
public class PartOne {
    public static void main(String[] args) {
        List<Apple> list = new ArrayList<>();
        BiFunctional<Color, Integer, Apple> createApple = Apple::new; //Create new object by using Constructor(Method) Reference

        for (int i = 0; i < 1000; i++) {
            if (i % 2 == 0) {
                //1. Behaviour Parametrization - when you passing code as a parameter of method
                //   (here is i'm passing "new Apple()" into add() method, do create an Apple, it can be anything, like "new DoStuff()")
                //   just passing a code into Parameter
                list.add(new Apple(Color.GREEN, i + 1));
            } else {
                list.add(createApple.doFunction(Color.RED, i + 1));
            }
        }


        {
            //Do a complex operation of Filter + GroupBy just in 2 lines using Lambda's and Stream functionality

            // 2. Anonymous Class - when you create an Instance of class that does not have a name, here it's
            //    class created by using Predicate<Apple> interface for .filter() and .groupingBy()
            Map<Color, List<Apple>> groupByColorUsingAnonymousClass = list.stream().filter(new Predicate<Apple>() {
                @Override
                public boolean test(Apple apple) {
                    return apple.isHeavy();
                }
            }).collect(Collectors.groupingBy(new Function<Apple, Color>() {
                @Override
                public Color apply(Apple apple) {
                    return apple.getColor();
                }
            }));
        }

        {
            //Do a complex operation of Filter + GroupBy just in 2 lines using Lambda's and Stream functionality

            // 3. Lambdas - when you passing a code as a parameter using Lambdas functionality
            //    using Lambdas, you should remember about TargetType - type of parameter(here it's a Predicate<Apple>) for .filter() and groupingBy
            //    Target Type should match Lambda, if Lambda will throw an Exception TargetType should also throw an Exception for it's method
            Map<Color, List<Apple>> groupByColor = list.stream().filter((Apple apple) -> apple.getHeight() < 300)
                    .collect(Collectors.groupingBy(Apple::getColor));
        }

        //Do a Filter operation by using MethodReference as a First-Class Java Citizens(References to the Objects(created by "new" keyword))
        // 4. Method Reference - when you passing a code as a parameter using Method References(Apple::isHeavy)
        list = Apple.filterApples(list, Apple::isHeavy);

        //Do a Filter operation by using Lambda Expression
        list = Apple.filterApples(list, filtering(Color.GREEN).negative());

        System.out.println("done: " + list);
    }

    //<Boolean> - type of Predicate/Function/Stream to be returned
    //Predicate<Apple> - is a Predicate Functional Interface
    private static <Boolean> learn.function.Predicate<Apple> filtering(Color color) {
        return (Apple apple) -> apple.getColor().equals(color);
    }
}
