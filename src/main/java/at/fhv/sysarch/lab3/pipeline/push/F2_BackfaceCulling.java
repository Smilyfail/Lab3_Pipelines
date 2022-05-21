package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;

public class F2_BackfaceCulling <I extends Face, O extends Face> extends Pushable<Face, Face> {
    public F2_BackfaceCulling(Pushable<Face, ?> target) {
        super(target);
    }

    @Override
    protected void push(Face face) {
        if (face == null) {
            target.push(null);
        } else {
            boolean shouldBeCulled = face.getV1().dot(face.getN1()) > 0;

            if (!shouldBeCulled) {
                target.push(face);
            }
        }
    }
}