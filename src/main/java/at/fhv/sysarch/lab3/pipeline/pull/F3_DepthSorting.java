package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class F3_DepthSorting<I extends Face, O extends Face> extends Pullable<Face, Face> {
    private List<Face> sortedFaces = new ArrayList<>();

    public F3_DepthSorting(Pullable<?, Face> source) {
        super(source);
    }

    @Override
    protected Face pull() {
        if (!source.isEmpty()) {
            List<Face> unsortedFaces = new ArrayList<>();

            while (!source.isEmpty()) unsortedFaces.add(source.pull());

            unsortedFaces.remove(unsortedFaces.size() - 1);

            sortedFaces = unsortedFaces.stream().sorted(Comparator.comparing(face -> face.getV1().getZ() + face.getV2().getZ() + face.getV3().getZ())).collect(Collectors.toList());
        }

        return isEmpty() ? null : sortedFaces.remove(0);
    }

    @Override
    protected boolean isEmpty() {
        return sortedFaces.isEmpty() && source.isEmpty();
    }
}