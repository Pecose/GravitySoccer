package entities.behavior.wrap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.world.PhysicsWorld;

public class WrapBehavior implements Behavior {
    // bornes horizontales du stade (1920 px → ±9.6 m)
    private static final float LEFT_M  = -960f / PhysicsWorld.PPM;
    private static final float RIGHT_M =  960f / PhysicsWorld.PPM;

    @Override
    public void update(Control control, Entity entity) {
        Body body = entity.getBody();
        if (body == null) return;

        Vector2 p = body.getPosition();
        // rayon en mètres
        float r = entity.getSize() / PhysicsWorld.PPM;

        // si tout le cercle est passé à droite
        if (p.x - r > RIGHT_M) {
            body.setTransform(LEFT_M  - r, p.y, body.getAngle());
        }
        // si tout le cercle est passé à gauche
        else if (p.x + r < LEFT_M) {
            body.setTransform(RIGHT_M + r, p.y, body.getAngle());
        }
    }
}
