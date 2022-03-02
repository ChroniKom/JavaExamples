package learn.function;

public interface Publisher<T> {
    void updates(Subscriber<? super T> subscriber);
}
