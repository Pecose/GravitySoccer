package entities.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public final class PhysicsWorld {
    public static final float PPM = 100f;
    private static final World WORLD = new World(new Vector2(0, 0), true);

    private PhysicsWorld() {}

    public static World getWorld() {
        return WORLD;
    }
}
