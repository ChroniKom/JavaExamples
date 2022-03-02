package learn.reactive;

import learn.function.Publisher;
import learn.function.Subscriber;

import java.util.ArrayList;
import java.util.List;
//import java.util.concurrent.Flow;

public class ReactiveObject implements Subscriber<String>, Publisher<String> {
    private final List<Subscriber<? super String>> subscribers = new ArrayList<>();

    private final String name;
    private String value;

    public ReactiveObject(String name) {
        this.name = name;
    }

    @Override
    public void updates(Subscriber<? super String> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void publish(String value) {
        this.value = value;
        System.out.println(this.name + " : " + this.value);
        subscribers.forEach(subscriber -> subscriber.publish(this.value));
    }
}
