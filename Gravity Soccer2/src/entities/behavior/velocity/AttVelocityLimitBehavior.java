package entities.behavior.velocity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import engine.Control;
import entities.Entity;
import entities.world.PhysicsWorld;

public class AttVelocityLimitBehavior implements VelocityLimitBehavior {
    /** Vitesse max en pixels/secondes que tu souhaites autoriser */
    private static final float MAX_SPEED_PX = 600f;

    @Override
    public void update(Control control, Entity entity) {
        Body body = entity.getBody();
        if (body == null) return;

        // 1) Récupère la vélocité en m/s
        Vector2 velM = body.getLinearVelocity();

        // 2) Convertis le max speed en m/s
        float maxSpeedMS = MAX_SPEED_PX / PhysicsWorld.PPM;

        // 3) Clamp la magnitude si nécessaire
        if (velM.len() > maxSpeedMS) {
            velM.nor().scl(maxSpeedMS);
            body.setLinearVelocity(velM);
        }
    }
}
