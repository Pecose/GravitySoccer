package entities.behavior.collision.edges;

import com.badlogic.gdx.physics.box2d.*;
import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.world.PhysicsWorld;

public class CollisionEdgeBehavior implements Behavior {
    private boolean initialized = false;

    @Override
    public void update(Control control, Entity entity) {
        if (initialized) return;

        Body body = entity.getBody();
        if (body == null) return;

        // Création d'un rectangle
        PolygonShape shape = new PolygonShape();
        float halfWidth = entity.getWidth() / PhysicsWorld.PPM;
        float halfHeight = entity.getHeight()/ PhysicsWorld.PPM;
        shape.setAsBox(halfWidth, halfHeight);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 0f; // inutile pour un StaticBody
        fd.restitution = 0f; // pas de rebond
        fd.friction = 0f; // optionnel : ajuster selon comportement voulu

        body.createFixture(fd);
        shape.dispose();

        initialized = true;
    }
}
