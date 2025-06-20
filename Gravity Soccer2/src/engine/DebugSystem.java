package engine;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class DebugSystem {
    private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    private final World world;
    private final Camera camera;
    private final float unitScale;

    public DebugSystem(World world, Camera camera, float unitScale) {
        this.world     = world;
        this.camera    = camera;
        this.unitScale = unitScale;
    }

    public void render() {
        // copie la matrice de la caméra
        Matrix4 debugMatrix = camera.combined.cpy();
        // scale X et Y par ton PPM (pixels per meter)
        debugMatrix.scale(unitScale, unitScale, 1f);
        // ensuite passe-la au renderer « classiquement »
        debugRenderer.render(world, debugMatrix);
    }

    public void dispose() {
        debugRenderer.dispose();
    }
}
