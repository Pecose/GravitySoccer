package entities.behavior.collision.ball;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.behavior.collision.bodys.CollisionBits;
import entities.world.PhysicsWorld;

public class CollisionBallBehavior implements Behavior {
    private boolean initialized = false;

    @Override
    public void update(Control control, Entity entity) {
    	if (!initialized) this.init(entity);
    }
    
    private void init(Entity entity) {
    	// 1) Récupérer le Body créé en Entity
        Body body = entity.getBody();
        if (body == null) return;

        // 2) Initialiser la fixture (une fois)
        CircleShape shape = new CircleShape();
        shape.setRadius(entity.getSize() / PhysicsWorld.PPM);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density     = 5f;
        fd.restitution = 1f;
        fd.friction    = 0f;
        
        fd.filter.categoryBits = CollisionBits.CATEGORY_BALL;
        fd.filter.maskBits = 
        		CollisionBits.CATEGORY_GOALZONE|
        		CollisionBits.CATEGORY_BUMPER |
        		CollisionBits.CATEGORY_EDGES |
        		CollisionBits.CATEGORY_PLAYER;
        
        body.createFixture(fd);
        shape.dispose();
        initialized = true;
    }
    
    public void resetInitializedFlag() {
        this.initialized = false;
    }
}
