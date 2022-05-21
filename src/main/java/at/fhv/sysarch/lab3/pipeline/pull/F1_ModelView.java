package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Mat4;

public class F1_ModelView<I extends Face, O extends Face> extends Pullable<Face, Face> {
    private Mat4 modelToViewMatrix;

    public F1_ModelView(Pullable<?, Face> source) {
        super(source);
    }

    @Override
    protected Face pull() {
        if (source.isEmpty()) return null;

        Face face = source.pull();

        return new Face(
                modelToViewMatrix.multiply(face.getV1()),
                modelToViewMatrix.multiply(face.getV2()),
                modelToViewMatrix.multiply(face.getV3()),
                modelToViewMatrix.multiply(face.getN1()),
                modelToViewMatrix.multiply(face.getN2()),
                modelToViewMatrix.multiply(face.getN3())
        );
    }

    @Override
    protected boolean isEmpty() {
        return source.isEmpty();
    }

    public void setModelToViewMatrix(Mat4 modelToViewMatrix) {
        this.modelToViewMatrix = modelToViewMatrix;
    }
}
