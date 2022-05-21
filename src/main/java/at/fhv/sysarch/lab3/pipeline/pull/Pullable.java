package at.fhv.sysarch.lab3.pipeline.pull;

public abstract class Pullable<I, O> {
    protected Pullable<?, I> source;

    public Pullable(Pullable<?, I> source) {
        this.source = source;
    }

    protected abstract O pull();

    protected abstract boolean isEmpty();
}