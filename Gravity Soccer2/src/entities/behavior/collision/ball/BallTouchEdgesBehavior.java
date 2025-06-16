package entities.behavior.collision.ball;

import com.badlogic.gdx.math.Vector2;

import edges.Edges;
import entities.Entity;
import entities.behavior.Behavior;
import entities.behavior.collision.bodys.CollisionReactive;

public class BallTouchEdgesBehavior implements Behavior, CollisionReactive {

    @Override
    public void update(engine.Control control, Entity entity) {
    }

    @Override
    public void onCollision(Entity self, Entity other) {
    	if (!(other instanceof Edges)) return; 
    	Vector2 velocity = self.getBody().getLinearVelocity();
        Vector2 impulse = velocity.cpy().nor().scl(0.5f); 
        self.getBody().applyLinearImpulse(impulse, self.getBody().getWorldCenter(), true);
    }

}
