package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

public class F0_Source<I extends Model, O extends Face> extends Pushable<Model, Face> {
    public F0_Source(Pushable<Face, ?> target) {
        super(target);
    }

    @Override
    protected void push(Model model) {
        model.getFaces().forEach(face -> target.push(face));

        target.push(null);
    }

    public void start(Model model) {
        push(model);
    }
}
