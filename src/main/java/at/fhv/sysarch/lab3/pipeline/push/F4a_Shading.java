package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Vec3;
import javafx.scene.paint.Color;

public class F4a_Shading<I extends Pair<Face, Color>, O extends Pair<Face, Color>> extends Pushable<Pair<Face, Color>, Pair<Face, Color>> {
    private final Vec3 lightPosition;

    public F4a_Shading(Pushable<Pair<Face, Color>, ?> target, Vec3 lightPosition) {
        super(target);
        this.lightPosition = lightPosition;
    }

    @Override
    protected void push(Pair<Face, Color> pair) {
        if (pair == null) {
            target.push(null);
        } else {
            Face face = pair.fst();
            Color color = pair.snd();

            float brightnessFactor = face.getN1().toVec3().getUnitVector().dot(lightPosition.getUnitVector());

            target.push(new Pair<>(face, color.deriveColor(0, 1, brightnessFactor, 1)));
        }
    }
}