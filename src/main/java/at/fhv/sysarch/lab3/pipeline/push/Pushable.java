package at.fhv.sysarch.lab3.pipeline.push;

public abstract class Pushable<I, O> {
    protected Pushable<O, ?> target;

    public Pushable(Pushable<O, ?> target) {
        this.target = target;
    }

    protected abstract void push(I object);
}