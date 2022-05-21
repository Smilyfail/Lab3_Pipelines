package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class F6_ScreenSpace<I extends Pair<Face, Color>, O extends Pair<Face, Color>> extends Pushable<Pair<Face, Color>, Pair<Face, Color>> {
    private final Mat4 viewToScreenMatrix;

    public F6_ScreenSpace(Pushable<Pair<Face, Color>, ?> target, Mat4 viewToScreenMatrix) {
        super(target);
        this.viewToScreenMatrix = viewToScreenMatrix;
    }

    @Override
    protected void push(Pair<Face, Color> pair) {
        if (pair == null) {
            target.push(null);
        } else {
            Face viewFace = pair.fst();

            Face screenFace = new Face(
                    viewToScreenMatrix.multiply(viewFace.getV1().multiply(1 / viewFace.getV1().getW())),
                    viewToScreenMatrix.multiply(viewFace.getV2().multiply(1 / viewFace.getV2().getW())),
                    viewToScreenMatrix.multiply(viewFace.getV3().multiply(1 / viewFace.getV3().getW())),
                    viewFace
            );

            target.push(new Pair<>(screenFace, pair.snd()));
        }
    }
}