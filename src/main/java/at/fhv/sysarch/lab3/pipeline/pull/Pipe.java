package at.fhv.sysarch.lab3.pipeline.pull;

public class Pipe<T> extends Pullable<T, T>{
    public Pipe(Pullable<?, T> source) {
        super(source);
    }

    @Override
    protected T pull() {
        return source.pull();
    }

    @Override
    protected boolean isEmpty() {
        return source.isEmpty();
    }
}
