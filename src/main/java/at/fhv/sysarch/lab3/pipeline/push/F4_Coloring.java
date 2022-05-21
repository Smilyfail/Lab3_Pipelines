package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class F4_Coloring<I extends Face, O extends Pair<Face, Color>> extends Pushable<Face, Pair<Face, Color>> {
    private final Color color;

    public F4_Coloring(Pushable<Pair<Face, Color>, ?> target, Color color) {
        super(target);
        this.color = color;
    }

    @Override
    protected void push(Face face) {
        if (face == null) {
            target.push(null);
        } else {
            target.push(new Pair<>(face, color));
        }
    }
}