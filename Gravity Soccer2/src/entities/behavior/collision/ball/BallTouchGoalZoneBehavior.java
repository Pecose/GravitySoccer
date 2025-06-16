package entities.behavior.collision.ball;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.world.PhysicsWorld;

public class BallTouchGoalZoneBehavior implements Behavior {
    private boolean initialized = false;

    @Override
    public void update(Control control, Entity entity) {
        if (initialized) return;
        Body body = entity.getBody();
        if (body == null) return;

        // 1) Création de la shape circulaire (rayon en mètres)
        CircleShape shape = new CircleShape();
        shape.setRadius(entity.getSize() / PhysicsWorld.PPM);

        // 2) Définition de la fixture
        FixtureDef fd = new FixtureDef();
        fd.shape       = shape;
        fd.density     = 5f;    // masse
        fd.restitution = 0.6f;  // rebond “élastique”
        fd.friction    = 0f;    // pas de frottement

        // 3) Filtrage : seule la balle (CAT_BALL) rebondit sur edges, bumpers, goal-zones, joueurs…
//        fd.filter.categoryBits
;

        // 4) On fixe la fixture sur le body
        body.createFixture(fd);
        shape.dispose();

        initialized = true;
    }
}
