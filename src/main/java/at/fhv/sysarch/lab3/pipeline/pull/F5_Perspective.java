package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class F5_Perspective<I extends Pair<Face, Color>, O extends Pair<Face, Color>> extends Pullable<Pair<Face, Color>, Pair<Face, Color>> {
    private final Mat4 orthographicToPerspectiveMatrix;

    public F5_Perspective(Pullable<?, Pair<Face, Color>> source, Mat4 orthographicToPerspectiveMatrix) {
        super(source);
        this.orthographicToPerspectiveMatrix = orthographicToPerspectiveMatrix;
    }

    @Override
    protected Pair<Face, Color> pull() {
        if (source.isEmpty()) return null;

        Pair<Face, Color> pair = source.pull();
        Face orthographicFace = pair.fst();
        Face perspectiveFace = new Face(
                orthographicToPerspectiveMatrix.multiply(orthographicFace.getV1()),
                orthographicToPerspectiveMatrix.multiply(orthographicFace.getV2()),
                orthographicToPerspectiveMatrix.multiply(orthographicFace.getV3()),
                orthographicFace
        );

        return new Pair<>(perspectiveFace, pair.snd());
    }

    @Override
    protected boolean isEmpty() {
        return source.isEmpty();
    }
}
