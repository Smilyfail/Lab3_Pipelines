package at.fhv.sysarch.lab3.pipeline.push;

public class Pipe<T> extends Pushable<T, T> {
    public Pipe(Pushable<T, ?> target) {
        super(target);
    }

    @Override
    protected void push(T object) {
        target.push(object);
    }
}
