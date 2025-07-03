package entities.behavior.collision.ball;

import engine.Control;
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
    
    @Override
    public void update(Control control, Entity entity) { }

    @Override
    public void onCollision(Entity self, Entity other) {
    	Control.soundManager.playNextNote();
    	
        if (!(other instanceof Player)) return;

        SideTeam side = ((Player) other).getSide();        
        Ball ball = (Ball)self;
        ball.setLastTeamTouched(side);

        GoalZone leftZone  = ((BlueGoal) Registry.get("LeftGoal")).getZone();
        GoalZone rightZone = ((RedGoal)  Registry.get("RightGoal")).getZone();

        if (side instanceof LeftTeam) {
            leftZone.addBehavior(GoalZoneBehavior.class, new GoalZoneTouchBehavior());
            rightZone.addBehavior(GoalZoneBehavior.class, new GoalZoneScoreBehavior());
            leftZone.setVisible(true);
            rightZone.setVisible(false);
        }
        else if (side instanceof RightTeam) {
            leftZone.addBehavior(GoalZoneBehavior.class, new GoalZoneScoreBehavior());
            rightZone.addBehavior(GoalZoneBehavior.class, new GoalZoneTouchBehavior());
            leftZone.setVisible(false);
            rightZone.setVisible(true);
        }
    }
}
