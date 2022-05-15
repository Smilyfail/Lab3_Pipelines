package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.obj.ObjLoader;
import at.fhv.sysarch.lab3.pipeline.Filter;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Source<I, O extends Model> extends Filter<I, O> {
    @Override
    public O pull() {
        try {
            File f = new File("resources/teapot.obj");
            Optional<Model> om = ObjLoader.loadModel(f);
            output = (O) om.get();
        } catch (IOException e) {
            System.err.println("Could not read model from file");
        }

        execute();

        return output;
    }

    @Override
    protected void execute() {

    }
}