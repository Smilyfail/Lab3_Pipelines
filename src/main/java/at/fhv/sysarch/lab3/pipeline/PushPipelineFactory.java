package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.*;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // 7. feed into the sink (renderer)
        F7_Renderer<Pair<Face, Color>, ?> rendererFilter = new F7_Renderer<>(null, pd.getGraphicsContext(), pd.getRenderingMode());

        // 6. perform perspective division to screen coordinates
        Pipe<Pair<Face, Color>> pipe6 = new Pipe<>(rendererFilter);
        F6_ScreenSpace<Pair<Face, Color>, Pair<Face, Color>> screenSpaceFilter = new F6_ScreenSpace<>(pipe6, pd.getViewportTransform());

        // 5. perform projection transformation
        Pipe<Pair<Face, Color>> pipe5 = new Pipe<>(screenSpaceFilter);
        F5_Perspective<Pair<Face, Color>, Pair<Face, Color>> perspectiveFilter = new F5_Perspective<>(pipe5, pd.getProjTransform());

        // 4a. perform lighting in VIEW SPACE
        Pipe<Pair<Face, Color>> pipe4a = new Pipe<>(perspectiveFilter);
        F4a_Shading<Pair<Face, Color>, Pair<Face, Color>> shadingFilter = new F4a_Shading<>(pipe4a, pd.getLightPos());

        // 4. add coloring (space unimportant)
        Pipe<Pair<Face, Color>> pipe4;
        if (pd.isPerformLighting()) {
            pipe4 = new Pipe<>(shadingFilter);
        } else {
            pipe4 = new Pipe<>(perspectiveFilter);
        }
        F4_Coloring<Face, Pair<Face, Color>> coloringFilter = new F4_Coloring<>(pipe4, pd.getModelColor());

        // 3. perform depth sorting in VIEW SPACE
        Pipe<Face> pipe3 = new Pipe<>(coloringFilter);
        F3_DepthSorting<Face, Face> depthSortingFilter = new F3_DepthSorting<>(pipe3);

        // 2. perform backface culling in VIEW SPACE
        Pipe<Face> pipe2 = new Pipe<>(depthSortingFilter);
        F2_BackfaceCulling<Face, Face> backfaceCullingFilter = new F2_BackfaceCulling<>(pipe2);

        // 1. perform model-view transformation from model to VIEW SPACE coordinates
        Pipe<Face> pipe1 = new Pipe<>(backfaceCullingFilter);
        F1_ModelView<Face, Face> modelViewFilter = new F1_ModelView<>(pipe1);

        // 0. push from the source (model)
        Pipe<Face> pipe0 = new Pipe<>(modelViewFilter);
        F0_Source<?, Face> sourceFilter = new F0_Source<>(pipe0);

        // returning an animation renderer which handles clearing of the
        // viewport and computation of the praction
        return new AnimationRenderer(pd) {
            // rotation variable goes in here
            float totalRotation = 0;

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer). 
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render 
             */
            @Override
            protected void render(float fraction, Model model) {
                // compute rotation in radians
                totalRotation += fraction;
                float radian = (float) (totalRotation % (2 * Math.PI));

                // create new model rotation matrix using pd.modelRotAxis
                Mat4 modelToWorldMatrix = pd.getModelTranslation();
                Mat4 rotationMatrix = Matrices.rotate(radian, pd.getModelRotAxis());
                Mat4 worldToViewMatrix = pd.getViewTransform();

                // compute updated model-view tranformation
                Mat4 modelToViewMatrix = worldToViewMatrix.multiply(rotationMatrix).multiply(modelToWorldMatrix);

                // update model-view filter
                modelViewFilter.setModelToViewMatrix(modelToViewMatrix);

                // TODO trigger rendering of the pipeline
                sourceFilter.start(model);
            }
        };
    }
}