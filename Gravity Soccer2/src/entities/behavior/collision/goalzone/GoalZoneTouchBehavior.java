package entities.behavior.collision.goalzone;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import engine.Control;
import entities.Entity;
import entities.behavior.collision.bodys.CollisionBits;
import entities.world.PhysicsWorld;

public class GoalZoneTouchBehavior implements GoalZoneBehavior {
    private boolean initialized = false;

    @Override
    public void update(Control control, Entity entity) {
        if (initialized) return;
        Body body = entity.getBody();
        if (body == null) return;

        // 1) On crée la shape rectangulaire
        PolygonShape shape = new PolygonShape();
        float halfW = entity.getWidth()  / PhysicsWorld.PPM;
        float halfH = entity.getHeight() / PhysicsWorld.PPM;
        // centre de la hit-box = moitié de la largeur/hauteur
        Vector2 center = new Vector2(halfW, halfH);
        // setAsBox(halfX, halfY, centre, angle)
        shape.setAsBox(halfW, halfH, center, 0f);

        // 2) Définition de la fixture avec rebond
        FixtureDef fd = new FixtureDef();
        fd.shape       = shape;
        fd.density     = 0f;    // static
        fd.restitution = 0.6f;  // rebond “classique”
        fd.friction    = 0f;

        // Optionnel : si tu veux que seule la balle rebondisse
        fd.filter.categoryBits = CollisionBits.CATEGORY_GOALZONE;
        fd.filter.maskBits     = CollisionBits.CATEGORY_BALL;

        body.createFixture(fd);
        shape.dispose();
        initialized = true;
    }

	@Override
	public void onCollision(Entity self, Entity other) {
		// TODO Auto-generated method stub
		
	}
}
