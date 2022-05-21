package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.*;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class PullPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // 0. pull from the source (model)
        F0_Source<?, Face> sourceFilter = new F0_Source<>();
        Pipe<Face> pipe0 = new Pipe<>(sourceFilter);

        // 1. perform model-view transformation from model to VIEW SPACE coordinates
        F1_ModelView<Face, Face> modelViewFilter = new F1_ModelView<>(pipe0);
        Pipe<Face> pipe1 = new Pipe<>(modelViewFilter);

        // 2. perform backface culling in VIEW SPACE
        F2_BackfaceCulling<Face, Face> backfaceCullingFilter = new F2_BackfaceCulling<>(pipe1);
        Pipe<Face> pipe2 = new Pipe<>(backfaceCullingFilter);

        // 3. perform depth sorting in VIEW SPACE
        F3_DepthSorting<Face, Face> depthSortingFilter = new F3_DepthSorting<>(pipe2);
        Pipe<Face> pipe3 = new Pipe<>(depthSortingFilter);

        // 4. add coloring (space unimportant)
        F4_Coloring<Face, Pair<Face, Color>> coloringFilter = new F4_Coloring<>(pipe3, pd.getModelColor());
        Pipe<Pair<Face, Color>> pipe4 = new Pipe<>(coloringFilter);

        // lighting can be switched on/off
        F5_Perspective<Pair<Face, Color>, Pair<Face, Color>> perspectiveFilter;
        if (pd.isPerformLighting()) {
            // 4a. perform lighting in VIEW SPACE
            F4a_Shading<Pair<Face, Color>, Pair<Face, Color>> shadingFilter = new F4a_Shading<>(pipe4, pd.getLightPos());
            Pipe<Pair<Face, Color>> pipe4a = new Pipe<>(shadingFilter);

            // 5. perform projection transformation on VIEW SPACE coordinates
            perspectiveFilter = new F5_Perspective<>(pipe4a, pd.getProjTransform());
        } else {
            // 5. perform projection transformation
            perspectiveFilter = new F5_Perspective<>(pipe4, pd.getProjTransform());
        }
        Pipe<Pair<Face, Color>> pipe5 = new Pipe<>(perspectiveFilter);

        // 6. perform perspective division to screen coordinates
        F6_ScreenSpace<Pair<Face, Color>, Pair<Face, Color>> screenSpaceFilter = new F6_ScreenSpace<>(pipe5, pd.getViewportTransform());
        Pipe<Pair<Face, Color>> pipe6 = new Pipe<>(screenSpaceFilter);

        // 7. feed into the sink (renderer)
        F7_Renderer<Pair<Face, Color>, ?> rendererFilter = new F7_Renderer<>(pipe6, pd.getGraphicsContext(), pd.getRenderingMode());

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

                // create new model rotation matrix using pd.getModelRotAxis and Matrices.rotate
                Mat4 modelToWorldMatrix = pd.getModelTranslation();
                Mat4 rotationMatrix = Matrices.rotate(radian, pd.getModelRotAxis());
                Mat4 worldToViewMatrix = pd.getViewTransform();

                // compute updated model-view transformation
                Mat4 modelToViewMatrix = worldToViewMatrix.multiply(rotationMatrix).multiply(modelToWorldMatrix);

                // update model-view filter
                modelViewFilter.setModelToViewMatrix(modelToViewMatrix);

                // trigger rendering of the pipeline
                sourceFilter.setModel(model);
                rendererFilter.start();
            }
        };
    }
}