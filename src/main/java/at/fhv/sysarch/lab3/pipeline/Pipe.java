package at.fhv.sysarch.lab3.pipeline;

public class Pipe<T> {
    private final Filter<?, T> inputFilter;
    private final Filter<T, ?> outputFilter;

    public Pipe(Filter<?, T> inputFilter, Filter<T, ?> outputFilter) {
        if (inputFilter == null || outputFilter == null) {
            throw new IllegalArgumentException("Pipe needs input and output filter");
        }

        this.inputFilter = inputFilter;
        this.outputFilter = outputFilter;
    }

    public T pull() {
        return inputFilter.pull();
    }

    public void push(T object) {
        outputFilter.push(object);
    }
}
