package learn.reactive;

public class Concatinator extends ReactiveObject{
    private String valueLeft;
    private String valueRight;

    public Concatinator(String name) {
        super(name);
    }

    public void setValueLeft(String valueLeft) {
        this.valueLeft = valueLeft;
        publish(this.valueLeft + this.valueRight);
    }

    public void setValueRight(String valueRight) {
        this.valueRight = valueRight;
        publish(this.valueLeft + this.valueRight);
    }
}
