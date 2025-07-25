package score;

import engine.Control;
import entities.Entity;
import entities.Registry;
import entities.world.PhysicsWorld;
import players.Ball;
import players.side.SideTeam;
import players.side.leftTeam.LeftTeam;
import players.side.rightTeam.RightTeam;

public class GoalManager {

    private static boolean goalPaused = false;

    public static void onGoalScored(SideTeam lastTouchSide) {
        if (goalPaused) return;
        goalPaused = true;
        
        Control.goalVideo.play("images/goal.webm", () -> {
        	if (lastTouchSide instanceof LeftTeam) {
                GameScore.ajouterButGauche();
            } else if (lastTouchSide instanceof RightTeam) {
                GameScore.ajouterButDroite();
            }

        	resetAllPositions();
            goalPaused = false;
        });
    }


    private static void resetAllPositions() {
        Entity old = Registry.getMap().remove("Ball");
        if (old != null && old.getBody() != null) {
            PhysicsWorld.getWorld().destroyBody(old.getBody());
        }
        Registry.add(new Ball(0, 0, 15), "Ball");
        Control.leftTeam.resetPlayers();
        Control.rightTeam.resetPlayers();
    }


	public static boolean isPaused() {
		return goalPaused;
	}
}