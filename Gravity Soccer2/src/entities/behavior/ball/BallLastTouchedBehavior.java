package entities.behavior.ball;

import entities.Entity;
import entities.Registry;
import entities.behavior.Behavior;
import entities.behavior.collision.bodys.CollisionReactive;
import entities.behavior.collision.goalzone.GoalZoneScoreBehavior;
import entities.behavior.collision.goalzone.GoalZoneTouchBehavior;
import players.Ball;
import players.Player;
import players.team.Side;

public class BallLastTouchedBehavior implements Behavior, CollisionReactive {
    private boolean initialized = false;
    private Ball ball;

    @Override
    public void update(engine.Control control, Entity entity) {
        if (!initialized) {
            this.ball = (Ball) entity;
            initialized = true;
        }
    }

    @Override
    public void onCollision(Entity self, Entity other) {
        if (other instanceof Player) {
            // quand Box2D détecte la collision, on set la team
            Side side = ((Player) other).getSide();
            
            ball.setLastTeamTouched(side);
            
            switch (side) {
	            case LEFT:
	            	Registry.get("LeftGoal").addBehavior(GoalZoneTouchBehavior.class, new GoalZoneTouchBehavior());
	            	Registry.get("RightGoal").addBehavior(GoalZoneScoreBehavior.class, new GoalZoneScoreBehavior());
	                break;
	            case RIGHT:
	            	Registry.get("LeftGoal").addBehavior(GoalZoneScoreBehavior.class, new GoalZoneScoreBehavior());
	            	Registry.get("RightGoal").addBehavior(GoalZoneTouchBehavior.class, new GoalZoneTouchBehavior());
	                break;
	            case NONE:
	            	break;
	        }
        }
    }
}
