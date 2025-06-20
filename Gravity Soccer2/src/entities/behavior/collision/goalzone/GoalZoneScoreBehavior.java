package entities.behavior.collision.goalzone;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import engine.Control;
import entities.Entity;
import entities.Registry;
import entities.behavior.collision.bodys.CollisionBits;
import entities.world.PhysicsWorld;
import goal.BlueGoal;
import goal.GoalZone;
import players.Ball;
import players.team.Side;
import score.GameScore;

public class GoalZoneScoreBehavior implements GoalZoneBehavior {
    private boolean initialized = false;
    private static final float MARGIN_PX = 5f;

    @Override
    public void update(Control control, Entity entity) {
        if (initialized) return;
        Body body = entity.getBody();
        if (body == null) return;

        float wPx = entity.getWidth()  - MARGIN_PX;
        float hPx = entity.getHeight() - MARGIN_PX;

        float halfW = (wPx / 2f) / PhysicsWorld.PPM;
        float halfH = (hPx / 2f) / PhysicsWorld.PPM;

        PolygonShape shape = new PolygonShape();
        Vector2 center = new Vector2(halfW, halfH + 0.04f);
        shape.setAsBox(halfW, halfH, center, 0f);

        FixtureDef fd = new FixtureDef();
        fd.shape          = shape;
        fd.isSensor       = true;
        fd.filter.categoryBits = CollisionBits.CATEGORY_GOALZONE;
        fd.filter.maskBits     = CollisionBits.CATEGORY_BALL;

        body.createFixture(fd);
        shape.dispose();

        initialized = true;
    }

    @Override
    public void onCollision(Entity self, Entity other) {

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
