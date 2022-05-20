package at.fhv.sysarch.lab3.pipeline.TESTS;

public abstract class Pullable<S, O> {
    protected Pullable<?, S> source;

    protected void setSource(Pullable<?, S> source) {
        this.source = source;
    };

    protected abstract O pull();

    protected abstract boolean hasNext();
}