package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Mat4;

public class F1_ModelView<I extends Face, O extends Face> extends Pushable<Face, Face> {
    private Mat4 modelToViewMatrix;

    public F1_ModelView(Pushable<Face, ?> target) {
        super(target);
    }

    @Override
    protected void push(Face face) {
        if (face == null) {
            target.push(null);
        } else {
            Face transformedFace = new Face(
                    modelToViewMatrix.multiply(face.getV1()),
                    modelToViewMatrix.multiply(face.getV2()),
                    modelToViewMatrix.multiply(face.getV3()),
                    modelToViewMatrix.multiply(face.getN1()),
                    modelToViewMatrix.multiply(face.getN2()),
                    modelToViewMatrix.multiply(face.getN3())
            );

            target.push(transformedFace);
        }
    }

    public void setModelToViewMatrix(Mat4 modelToViewMatrix) {
        this.modelToViewMatrix = modelToViewMatrix;
    }
}