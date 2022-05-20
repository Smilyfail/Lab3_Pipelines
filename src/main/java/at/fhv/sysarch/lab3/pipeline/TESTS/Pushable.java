package at.fhv.sysarch.lab3.pipeline.TESTS;

public abstract class Pushable<I, T> {
    protected Pushable<T, ?> target;

    protected void setTarget(Pushable<T, ?> target) {
        this.target = target;
    };

    protected abstract void push(I object);
}