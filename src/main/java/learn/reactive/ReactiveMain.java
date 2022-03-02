package learn.reactive;

public class ReactiveMain {
    public static void main(String[] args) {
        Concatinator concatinator = new Concatinator("concatinator");
        ReactiveObject a = new ReactiveObject("a");
        ReactiveObject b = new ReactiveObject("b");
        ReactiveObject c = new ReactiveObject("c");

        a.updates(concatinator::setValueLeft);
        b.updates(concatinator::setValueRight);
        a.updates(c);

        a.publish("1");
        b.publish("2");
        a.publish("3");
    }
}
