package entities.behavior.collision.goalzone;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import engine.Control;
import entities.Entity;
import entities.behavior.collision.bodys.CollisionBits;
import entities.world.PhysicsWorld;
import players.Ball;
import players.side.SideTeam;
import players.side.leftTeam.LeftTeam;
import players.side.rightTeam.RightTeam;
import score.GameScore;
import score.GoalManager;

public class GoalZoneScoreBehavior implements GoalZoneBehavior {
    private boolean initialized = false;
    private static final float MARGIN_PX = 5f;

    @Override
    public void update(Control control, Entity entity) {
        if (initialized) return;
        Body body = entity.getBody();
        if (body == null) return;

        // Supprime toute fixture existante pour éviter doublons
        for (Fixture f : body.getFixtureList()) {
            body.destroyFixture(f);
        }

        // Dimensions ajustées
        float wPx = entity.getWidth() - MARGIN_PX;
        float hPx = entity.getHeight() - MARGIN_PX;
        float halfW = (wPx / 2f) / PhysicsWorld.PPM;
        float halfH = (hPx / 2f) / PhysicsWorld.PPM;

        // Shape et fixture capteur
        PolygonShape shape = new PolygonShape();
        Vector2 center = new Vector2(halfW + 0.02f, halfH + 0.04f);
        shape.setAsBox(halfW, halfH, center, 0f);

        FixtureDef fd = new FixtureDef();
        fd.shape       = shape;
        fd.isSensor    = true;
        fd.filter.categoryBits = CollisionBits.CATEGORY_GOALZONE;
        fd.filter.maskBits     = CollisionBits.CATEGORY_BALL;

        Fixture fixture = body.createFixture(fd);
        fixture.setUserData(entity);
        shape.dispose();

        initialized = true;
    }

    @Override
    public void onCollision(Entity self, Entity other) {
    	if (!(other instanceof Ball)) return;
        Ball ball = (Ball) other;
        SideTeam side = ball.getLastTeamTouched();
        if (side instanceof LeftTeam) {
            GoalManager.onLeftGoalScored();
        } else if (side instanceof RightTeam) {
            GoalManager.onRightGoalScored();
        }
    }
}
