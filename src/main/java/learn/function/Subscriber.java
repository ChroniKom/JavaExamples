package learn.function;

public interface Subscriber<T> {
    void publish(T t);
}
