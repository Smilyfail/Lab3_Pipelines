package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Filter;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import javafx.scene.canvas.GraphicsContext;

public class Sink<I extends Face, O> extends Filter<I, O> {
    private final GraphicsContext graphicsContext;
    private final RenderingMode renderingMode;

    public Sink(GraphicsContext graphicsContext, RenderingMode renderingMode) {
        this.graphicsContext = graphicsContext;
        this.renderingMode = renderingMode;
    }

    @Override
    protected void execute() {
        if(renderingMode == RenderingMode.POINT) {
            graphicsContext.fillOval(input.getV1().getX(), input.getV1().getY(), 5,5);
        }
        else {
            graphicsContext.strokeLine(input.getV1().getX(), input.getV1().getY(), input.getV2().getX(), input.getV2().getY());
            graphicsContext.strokeLine(input.getV1().getX(), input.getV1().getY(), input.getV3().getX(), input.getV3().getY());
            graphicsContext.strokeLine(input.getV2().getX(), input.getV2().getY(), input.getV3().getX(), input.getV3().getY());
        }
    }
}
