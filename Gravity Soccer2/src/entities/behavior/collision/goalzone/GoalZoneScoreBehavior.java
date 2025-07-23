package entities.behavior.collision.goalzone;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import engine.Control;
import entities.Entity;
import goal.GoalZone;
import players.Ball;
import players.side.SideTeam;
import players.side.leftTeam.LeftTeam;
import players.side.rightTeam.RightTeam;
import score.GoalManager;

public class GoalZoneScoreBehavior implements GoalZoneBehavior {

    @Override
    public void render(Control control, Entity entity) {
    	((GoalZone)entity).getGoal().getFixtureManager().getFixture("goalzone").setSensor(true);
        // Debug visuel : on affiche la zone du but en rouge transparent
        Gdx.gl.glEnable(GL20.GL_BLEND);
        control.renderer.setColor(new Color(100f, 100f, 100f, 0.3f));
        control.renderer.rect(
            entity.getPosX(),
            entity.getPosY(),
            entity.getWidth(),
            entity.getHeight()
        );
        control.renderer.setColor(Color.WHITE);
    }

    @Override
    public void onCollision(Entity self, Entity other) {
        if (!(other instanceof Ball)) return;

        Ball ball = (Ball) other;
        SideTeam side = ball.getLastTeamTouched();

        // 📢 Logique de score
        if (side instanceof LeftTeam) {
            GoalManager.onLeftGoalScored();
        } else if (side instanceof RightTeam) {
            GoalManager.onRightGoalScored();
        }
    }

    @Override
    public void batch(Control control, Entity entity) {
        // Pas de batch particulier
    }
}
