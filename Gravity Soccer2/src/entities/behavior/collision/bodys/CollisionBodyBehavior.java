package entities.behavior.collision.bodys;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.world.PhysicsWorld;

public class CollisionBodyBehavior implements Behavior, CollisionReactive {
    private boolean initialized = false;

    @Override
    public void update(Control control, Entity entity) {
        if (initialized) return;
        Body body = entity.getBody();
        if (body == null) return;

        // Shape circulaire
        CircleShape shape = new CircleShape();
        shape.setRadius(entity.getSize() / PhysicsWorld.PPM);

        // FixtureDef
        FixtureDef fd = new FixtureDef();
        fd.shape       = shape;
        fd.density     = 5f;
        fd.restitution = 0.6f;
        fd.friction    = 0f;

        // Filtrage collisions
        fd.filter.categoryBits = CollisionBits.CATEGORY_PLAYER;
        fd.filter.maskBits     =
              CollisionBits.CATEGORY_BUMPER
            | CollisionBits.CATEGORY_EDGES    
            | CollisionBits.CATEGORY_BALL
            | CollisionBits.CATEGORY_GOALZONE
            ;

        // Création et userData
        Fixture fx = body.createFixture(fd);
        fx.setUserData(entity);

        // Cleanup
        shape.dispose();
        initialized = true;
    }
    
    public void resetInitializedFlag() {
        this.initialized = false;
    }

	@Override
	public void onCollision(Entity self, Entity other) {
	}
}
