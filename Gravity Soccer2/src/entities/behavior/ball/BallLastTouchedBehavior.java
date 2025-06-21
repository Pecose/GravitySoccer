package entities.behavior.ball;

import entities.Entity;
import entities.Registry;
import entities.behavior.Behavior;
import entities.behavior.collision.bodys.CollisionReactive;
import entities.behavior.collision.goalzone.GoalZoneBehavior;
import entities.behavior.collision.goalzone.GoalZoneScoreBehavior;
import entities.behavior.collision.goalzone.GoalZoneTouchBehavior;
import goal.BlueGoal;
import goal.GoalZone;
import goal.RedGoal;
import players.Ball;
import players.Player;
import players.side.SideTeam;
import players.side.leftTeam.LeftTeam;
import players.side.rightTeam.RightTeam;

public class BallLastTouchedBehavior implements Behavior, CollisionReactive {
    
    private GoalZoneTouchBehavior touch = new GoalZoneTouchBehavior();
    private GoalZoneScoreBehavior score = new GoalZoneScoreBehavior();

    @Override
    public void update(engine.Control control, Entity entity) {
        // pas besoin
    }

    @Override
    public void onCollision(Entity self, Entity other) {
        if (!(other instanceof Player)) return;

        SideTeam side = ((Player) other).getSide();        
        Ball ball = (Ball) Registry.get("Ball");
        ball.setLastTeamTouched(side);

        GoalZone leftZone  = ((BlueGoal) Registry.get("LeftGoal")).getZone();
        GoalZone rightZone = ((RedGoal)  Registry.get("RightGoal")).getZone();

        if (side instanceof LeftTeam) {
            leftZone.addBehavior(GoalZoneBehavior.class, touch);
            rightZone.addBehavior(GoalZoneBehavior.class, score);
            leftZone.setVisible(true);
            rightZone.setVisible(false);
        }
        else if (side instanceof RightTeam) {
            leftZone.addBehavior(GoalZoneBehavior.class, score);
            rightZone.addBehavior(GoalZoneBehavior.class, touch);
            leftZone.setVisible(false);
            rightZone.setVisible(true);
        }
        // sinon, pas de change
    }
}
