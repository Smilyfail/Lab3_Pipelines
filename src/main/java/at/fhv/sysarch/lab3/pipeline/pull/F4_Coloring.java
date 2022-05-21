package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class F4_Coloring<I extends Face, O extends Pair<Face, Color>> extends Pullable<Face, Pair<Face, Color>> {
    private final Color color;

    public F4_Coloring(Pullable<?, Face> source, Color color) {
        super(source);
        this.color = color;
    }

    @Override
    protected Pair<Face, Color> pull() {
        if (source.isEmpty()) return null;

        return new Pair<>(source.pull(), color);
    }

    @Override
    protected boolean isEmpty() {
        return source.isEmpty();
    }
}
