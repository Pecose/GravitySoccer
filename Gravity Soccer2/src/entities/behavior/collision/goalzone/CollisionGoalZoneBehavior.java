package entities.behavior.collision.goalzone;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.world.PhysicsWorld;

public class CollisionGoalZoneBehavior implements Behavior {
    private boolean initialized = false;

    @Override
    public void update(Control control, Entity entity) {
        if (initialized) return;

        Body body = entity.getBody();
        if (body == null) return;

        // Dimensions du rectangle en mètres
        float halfWidth  = (entity.getWidth()  * 0.5f) / PhysicsWorld.PPM;
        float halfHeight = (entity.getHeight() * 0.5f) / PhysicsWorld.PPM;

        // Décalage pour centrer le fixture sur le centre du corps
        Vector2 centerOffset = new Vector2(halfWidth, halfHeight);

        // Création du shape centré sur centerOffset
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(halfWidth, halfHeight, centerOffset, 0f);

        // Définition du fixture (static body, restitution pour rebond parfait)
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 0f;            // inutile pour StaticBody
        fd.restitution = 1f;        // rebond parfait
        fd.friction    = 0f;        // pas de frottement

        body.createFixture(fd);
        shape.dispose();

        initialized = true;
    }
}
