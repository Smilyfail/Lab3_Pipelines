package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class F5_Perspective<I extends Pair<Face, Color>, O extends Pair<Face, Color>> extends Pushable<Pair<Face, Color>, Pair<Face, Color>> {
    private final Mat4 orthographicToPerspectiveMatrix;

    public F5_Perspective(Pushable<Pair<Face, Color>, ?> target, Mat4 orthographicToPerspectiveMatrix) {
        super(target);
        this.orthographicToPerspectiveMatrix = orthographicToPerspectiveMatrix;
    }

    @Override
    protected void push(Pair<Face, Color> pair) {
        if (pair == null) {
            target.push(null);
        } else {
            Face orthographicFace = pair.fst();

            Face perspectiveFace = new Face(
                    orthographicToPerspectiveMatrix.multiply(orthographicFace.getV1()),
                    orthographicToPerspectiveMatrix.multiply(orthographicFace.getV2()),
                    orthographicToPerspectiveMatrix.multiply(orthographicFace.getV3()),
                    orthographicFace
            );

            target.push(new Pair<>(perspectiveFace, pair.snd()));
        }
    }
}