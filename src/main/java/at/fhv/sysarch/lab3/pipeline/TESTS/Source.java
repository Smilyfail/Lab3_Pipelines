package at.fhv.sysarch.lab3.pipeline.TESTS;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

public class Source<I extends Model, O extends Face> extends Pushable<Model, Face> implements Filter {
    @Override
    public void execute() {
        // do things here
    }

    @Override
    protected void push(Model object) {
        execute();

        // target.push(face);
    }
}