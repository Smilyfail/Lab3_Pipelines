package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class F6_ScreenSpace<I extends Pair<Face, Color>, O extends Pair<Face, Color>> extends Pullable<Pair<Face, Color>, Pair<Face, Color>> {
    private final Mat4 viewToScreenMatrix;

    public F6_ScreenSpace(Pullable<?, Pair<Face, Color>> source, Mat4 viewToScreenMatrix) {
        super(source);
        this.viewToScreenMatrix = viewToScreenMatrix;
    }

    @Override
    protected Pair<Face, Color> pull() {
        if (source.isEmpty()) return null;

        Pair<Face, Color> pair = source.pull();
        Face viewFace = pair.fst();
        Face screenFace = new Face(
                viewToScreenMatrix.multiply(viewFace.getV1().multiply(1 / viewFace.getV1().getW())),
                viewToScreenMatrix.multiply(viewFace.getV2().multiply(1 / viewFace.getV2().getW())),
                viewToScreenMatrix.multiply(viewFace.getV3().multiply(1 / viewFace.getV3().getW())),
                viewFace
        );

        return new Pair<>(screenFace, pair.snd());
    }

    @Override
    protected boolean isEmpty() {
        return source.isEmpty();
    }
}
