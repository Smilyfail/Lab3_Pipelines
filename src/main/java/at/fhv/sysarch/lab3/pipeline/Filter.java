package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;

public class Filter<I extends Face> implements IFilter {
    private Pipe successor;

    @Override
    public void setPipeSuccessor(Pipe pipeSuccessor) {
        this.successor = pipeSuccessor;
    }

    @Override
    public void write(I face) {
        Face face1 = new Face(face.getV1().multiply(100), face.getV2().multiply(100), face.getV3().multiply(100), face.getN1(), face.getN2(), face.getN3());
        successor.write(face1);
    }
}
