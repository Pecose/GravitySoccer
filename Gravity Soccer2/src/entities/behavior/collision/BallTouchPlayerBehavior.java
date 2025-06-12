package entities.behavior.collision;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import entities.Entity;
import entities.behavior.Behavior;
import players.Player;

public class BallTouchPlayerBehavior implements Behavior, CollisionReactive {

    @Override
    public void update(engine.Control control, Entity entity) {
        // Rien à faire à chaque frame pour ce comportement
    }

    @Override
    public void onCollision(Entity self, Entity other) {
    	if (!(other instanceof Player)) return; 
        if (other.getBody() != null && other.getBody().getType() != BodyDef.BodyType.StaticBody) {
            Vector2 normal = other.getBody().getLinearVelocity(); 
            Vector2 impulse = normal.scl(1f); // 2f = intensité du boost
            other.getBody().applyLinearImpulse(impulse, other.getBody().getWorldCenter(), true);
        }
    }

}
