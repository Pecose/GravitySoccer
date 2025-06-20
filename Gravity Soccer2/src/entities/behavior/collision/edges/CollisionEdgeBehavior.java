package entities.behavior.collision.edges;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

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

        // 1) on calcule les vrais demi-extents en mètres
        float halfW = (entity.getWidth()  / 2f) / PhysicsWorld.PPM;
        float halfH = (entity.getHeight() / 2f) / PhysicsWorld.PPM;

        // 2) on crée la shape, décalée si besoin
        PolygonShape shape = new PolygonShape();

        // Si ton Body est déjà centré :
//      shape.setAsBox(halfW, halfH);

        // Si ton Body est au coin bas-gauche :
        Vector2 center = new Vector2(halfW, halfH);
        shape.setAsBox(halfW, halfH, center, 0f);

        // 3) la fixture
        FixtureDef fd = new FixtureDef();
        fd.shape       = shape;
        fd.density     = 0f;
        fd.restitution = 0f;
        fd.friction    = 0f;
        body.createFixture(fd);

        shape.dispose();
        initialized = true;
    }
}
