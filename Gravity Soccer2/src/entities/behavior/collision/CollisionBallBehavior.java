package entities.behavior.collision;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.world.PhysicsWorld;

public class CollisionBallBehavior implements Behavior {
    private boolean initialized = false;

    @Override
    public void update(Control control, Entity entity) {
    	if (initialized) return;
        // 1) Récupérer le Body créé en Entity
        Body body = entity.getBody();
        if (body == null) return;

        // 2) Initialiser la fixture (une fois)
        CircleShape shape = new CircleShape();
        // radius stocké en pixels → convertir en mètres
        shape.setRadius(entity.getSize() / PhysicsWorld.PPM);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density     = 5f;
        fd.restitution = 0.6f;
        fd.friction    = 0f;
        
        fd.filter.categoryBits = CollisionBits.CATEGORY_BALL;
        fd.filter.maskBits = -1; 

        body.createFixture(fd);
        shape.dispose();
        initialized = true;

    }
}
