package at.fhv.sysarch.lab3.pipeline;

public abstract class Filter<I, O> {
    protected Pipe<I> inputPipe;
    protected Pipe<O> outputPipe;
    protected I input;
    protected O output;

    public Filter() {

    }

    public Filter(Pipe<I> inputPipe, Pipe<O> outputPipe) {
        this.inputPipe = inputPipe;
        this.outputPipe = outputPipe;
    }

    public void setInputPipe(Pipe<I> inputPipe) {
        this.inputPipe = inputPipe;
    }

    public void setOutputPipe(Pipe<O> outputPipe) {
        this.outputPipe = outputPipe;
    }

    public O pull() {
        if (inputPipe != null) {
            input = inputPipe.pull();
        }

        execute();

        return output;
    }

    public void push(I input) {
        this.input = input;

        execute();

        if (outputPipe != null) {
            outputPipe.push(output);
        }
    }

    protected abstract void execute();
}
