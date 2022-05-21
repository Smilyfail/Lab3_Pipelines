package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

import java.util.ArrayList;
import java.util.List;

public class F0_Source<I, O extends Face> extends Pullable<I, Face>{
    private List<Face> faces;

    public F0_Source() {
        super(null);
    }

    @Override
    protected Face pull() {
        return isEmpty() ? null : faces.remove(faces.size() - 1);
    }

    @Override
    protected boolean isEmpty() {
        return faces.isEmpty();
    }

    public void setModel(Model model) {
        this.faces = new ArrayList<>(model.getFaces());
    }
}
