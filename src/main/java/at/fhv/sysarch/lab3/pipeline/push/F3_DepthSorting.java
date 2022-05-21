package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class F3_DepthSorting<I extends Face, O extends Face> extends Pushable<Face, Face> {
    private final List<Face> unsortedFaces = new ArrayList<>();

    public F3_DepthSorting(Pushable<Face, ?> target) {
        super(target);
    }

    @Override
    protected void push(Face face) {
        if (face == null) {
            List<Face> sortedFaces = unsortedFaces.stream().sorted(Comparator.comparing(f -> f.getV1().getZ() + f.getV2().getZ() + f.getV3().getZ())).collect(Collectors.toList());
            sortedFaces.forEach(f -> target.push(f));
            unsortedFaces.clear();
        } else {
            unsortedFaces.add(face);
        }
    }
}