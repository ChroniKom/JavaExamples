package learn.function;

@FunctionalInterface
public interface Predicate<T> {
    boolean doPredicate(T t);

    default Predicate<T> negative() {
        return (T t) -> !this.doPredicate(t);
    }
}
