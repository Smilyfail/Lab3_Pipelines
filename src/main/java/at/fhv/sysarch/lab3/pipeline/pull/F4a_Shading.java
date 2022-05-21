package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Vec3;
import javafx.scene.paint.Color;

public class F4a_Shading<I extends Pair<Face, Color>, O extends Pair<Face, Color>> extends Pullable<Pair<Face, Color>, Pair<Face, Color>> {
    private final Vec3 lightPosition;

    public F4a_Shading(Pullable<?, Pair<Face, Color>> source, Vec3 lightPosition) {
        super(source);
        this.lightPosition = lightPosition;
    }

    @Override
    protected Pair<Face, Color> pull() {
        if (source.isEmpty()) return null;

        Pair<Face, Color> pair = source.pull();
        Face face = pair.fst();
        Color color = pair.snd();

        float brightnessFactor = face.getN1().toVec3().getUnitVector().dot(lightPosition.getUnitVector());

        return new Pair<>(face, color.deriveColor(0, 1, brightnessFactor, 1));
    }

    @Override
    protected boolean isEmpty() {
        return source.isEmpty();
    }
}
