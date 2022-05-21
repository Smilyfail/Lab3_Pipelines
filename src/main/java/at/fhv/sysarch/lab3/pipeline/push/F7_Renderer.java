package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import com.hackoeur.jglm.Vec2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class F7_Renderer<I extends Pair<Face, Color>, O> extends Pushable<Pair<Face, Color>, O> {
    private final GraphicsContext graphicsContext;
    private final RenderingMode renderingMode;

    public F7_Renderer(Pushable<O, ?> target, GraphicsContext graphicsContext, RenderingMode renderingMode) {
        super(target);
        this.graphicsContext = graphicsContext;
        this.renderingMode = renderingMode;
    }

    @Override
    protected void push(Pair<Face, Color> pair) {
        if (pair != null) {
            Face face = pair.fst();
            Color color = pair.snd();

            Vec2 v1 = face.getV1().toScreen();
            Vec2 v2 = face.getV2().toScreen();
            Vec2 v3 = face.getV3().toScreen();

            graphicsContext.setFill(color);
            graphicsContext.setStroke(color);

            if (renderingMode == RenderingMode.POINT) {
                graphicsContext.fillOval(v1.getX(), v1.getY(), 5, 5);
            } else {
                graphicsContext.strokeLine(v1.getX(), v1.getY(), v2.getX(), v2.getY());
                graphicsContext.strokeLine(v2.getX(), v2.getY(), v3.getX(), v3.getY());
                graphicsContext.strokeLine(v3.getX(), v3.getY(), v1.getX(), v1.getY());

                if (renderingMode == RenderingMode.FILLED) {
                    graphicsContext.fillPolygon(new double[]{v1.getX(), v2.getX(), v3.getX()}, new double[]{v1.getY(), v2.getY(), v3.getY()}, 3);
                }
            }
        }
    }
}