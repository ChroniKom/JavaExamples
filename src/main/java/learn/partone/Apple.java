package learn.partone;

import learn.function.Predicate;
import learn.util.Color;

import java.util.ArrayList;
import java.util.List;

public class Apple {
    public static final int HIGH_HEIGHT = 150;

    private final Color color;
    private final int height;

    public Apple(Color color, int height) {
        this.color = color;
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public int getHeight() {
        return height;
    }

    public boolean isHeavy() {
        return height > HIGH_HEIGHT;
    }

    //can filter by any parameter wanted(Color, Weight or even Size or newly created parameter in future by using Predicate<>)
    //instead of creating each method for each type of filter, it can be done in one method with Predicate
    public static List<Apple> filterApples(List<Apple> list, Predicate<Apple> predicate) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : list) {
            if (predicate.doPredicate(apple)) {
                result.add(apple);
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "color=" + color +
                ", height=" + height +
                '}';
    }
}
