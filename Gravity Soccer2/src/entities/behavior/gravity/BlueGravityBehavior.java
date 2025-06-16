package entities.behavior.gravity;

import com.badlogic.gdx.physics.box2d.Body;
import engine.Control;
import entities.Entity;
import entities.behavior.collision.bodys.CollisionReactive;
import entities.world.PhysicsWorld;

public class BlueGravityBehavior implements CollisionReactive, GravityBehavior {
    private final float accelX = 300f;   // *valeur à affiner* (px/s²)

    @Override
    public void update(Control control, Entity entity) {
        Body body = entity.getBody();    // le Body unique, créé dans Entity
        if (body == null) return;

        // F = m·a   (on convertit accelX en m/s²)
        float aMeters = accelX / PhysicsWorld.PPM;
        float forceX  = body.getMass() * aMeters;
        body.applyForceToCenter(forceX, 0f, true);
    }
    
    @Override
    public void onCollision(Entity self, Entity other) {
    	self.addBehavior(GravityBehavior.class, new RedGravityBehavior());
    }
}
