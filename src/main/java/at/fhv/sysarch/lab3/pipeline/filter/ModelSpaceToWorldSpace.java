package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Filter;
import com.hackoeur.jglm.Vec4;

public class ModelSpaceToWorldSpace<I extends Face, O extends Face> extends Filter<I, O> {
    @Override
    protected void execute() {
        output = (O) new Face(new Vec4(input.getV1().toVec3(), 1), new Vec4(input.getV2().toVec3(), 1), new Vec4(input.getV3().toVec3(), 1), input);
    }
}
