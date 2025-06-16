package entities.behavior.collision.goalzone;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import engine.Control;
import entities.Entity;
import entities.behavior.collision.bodys.CollisionBits;
import entities.world.PhysicsWorld;
import players.Ball;
import players.team.Side;
import score.GameScore;

public class GoalZoneScoreBehavior implements GoalZoneBehavior {
    private boolean initialized = false;

    @Override
    public void update(Control control, Entity entity) {
        if (initialized) return;
        Body body = entity.getBody();
        if (body == null) return;

        PolygonShape shape = new PolygonShape();
        float halfW = entity.getWidth()  / PhysicsWorld.PPM;
        float halfH = entity.getHeight() / PhysicsWorld.PPM;
        shape.setAsBox(halfW, halfH, new Vector2(halfW, halfH), 0f);

        FixtureDef fd = new FixtureDef();
        fd.shape    = shape;
        fd.isSensor = true;
        // on ne veut réagir qu’à la balle
        fd.filter.categoryBits = CollisionBits.CATEGORY_GOALZONE;
        fd.filter.maskBits     = CollisionBits.CATEGORY_BALL;

        body.createFixture(fd);
        shape.dispose();
        initialized = true;
    }

    @Override
    public void onCollision(Entity self, Entity other) {
        // *uniquement* la balle, et *uniquement* au beginContact
        if (!(other instanceof Ball)) return;
        Ball ball = (Ball) other;

        Side side = ball.getLastTeamTouched();
        if (side == Side.LEFT) {
            GameScore.ajouterButGauche();
        } else if (side == Side.RIGHT) {
            GameScore.ajouterButDroite();
        }
    }
}
