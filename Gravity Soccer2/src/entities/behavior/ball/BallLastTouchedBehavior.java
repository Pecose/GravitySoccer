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
import players.team.Side;

public class BallLastTouchedBehavior implements Behavior, CollisionReactive {
	
	private GoalZoneTouchBehavior goalZoneTouchBehavior  = new GoalZoneTouchBehavior();
	private GoalZoneScoreBehavior goalZoneScoreBehavior  = new GoalZoneScoreBehavior();

    @Override
    public void update(engine.Control control, Entity entity) {
    }

    @Override
    public void onCollision(Entity self, Entity other) {
        if (other instanceof Player) {
            Side side = ((Player) other).getSide();
            
            Ball ball = ((Ball)Registry.get("Ball"));
            ball.setLastTeamTouched(side);
            
            GoalZone leftZone = (GoalZone)((BlueGoal)Registry.get("LeftGoal")).getZone();
            GoalZone rightZone = (GoalZone)((RedGoal)Registry.get("RightGoal")).getZone();
            
            switch (side) {
	            case LEFT:
	            	leftZone.addBehavior(GoalZoneBehavior.class, goalZoneTouchBehavior);
	            	rightZone.addBehavior(GoalZoneBehavior.class, goalZoneScoreBehavior);
//	            	((Ball)Registry.get("Ball")).setColor(Color.BLUE);
	            	leftZone.setVisible(true);
	            	rightZone.setVisible(false);
	                break;
	            case RIGHT:
	            	leftZone.addBehavior(GoalZoneBehavior.class, goalZoneScoreBehavior);
	            	rightZone.addBehavior(GoalZoneBehavior.class, goalZoneTouchBehavior);
//	            	((Ball)Registry.get("Ball")).setColor(Color.RED);
	            	leftZone.setVisible(false);
	            	rightZone.setVisible(true);
	                break;
	            case NONE:
	            	break;
	        }
        }
    }

}
