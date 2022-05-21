package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;

public class F2_BackfaceCulling<I extends Face, O extends Face> extends Pullable<Face, Face> {
    public F2_BackfaceCulling(Pullable<?, Face> source) {
        super(source);
    }

    @Override
    protected Face pull() {
        if (source.isEmpty()) return null;

        Face face = source.pull();

        boolean shouldBeCulled = face.getV1().dot(face.getN1()) > 0;

        return shouldBeCulled ? pull() : face;
    }

    @Override
    protected boolean isEmpty() {
        return source.isEmpty();
    }
}
