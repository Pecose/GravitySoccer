package entities.behavior.collision;

import com.badlogic.gdx.math.Vector2;

import edges.Edges;
import entities.Entity;
import entities.behavior.Behavior;

public class BallTouchEdgesBehavior implements Behavior, CollisionReactive {

    @Override
    public void update(engine.Control control, Entity entity) {
    }

    @Override
    public void onCollision(Entity self, Entity other) {
    	if (!(other instanceof Edges)) return; 
    	Vector2 velocity = self.getBody().getLinearVelocity();

        // Direction opposée (rebond) + boost
        Vector2 impulse = velocity.cpy().nor().scl(0.5f); // Plus ou moins selon le boost désiré

        // Appliquer à la balle (self)
        self.getBody().applyLinearImpulse(impulse, self.getBody().getWorldCenter(), true);
    }

}
