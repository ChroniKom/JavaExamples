package learn.function;

@FunctionalInterface
public interface BiFunctional<A, B, T> {
    T doFunction(A a, B b);
}
