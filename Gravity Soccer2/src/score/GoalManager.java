package score;

import com.badlogic.gdx.utils.Timer;

import engine.Control;
import entities.Entity;
import entities.Registry;
import entities.world.PhysicsWorld;
import players.Ball;

public class GoalManager {
    private static boolean goalPaused = false;

    public static void onLeftGoalScored() {
        if (goalPaused) return;            
        goalPaused = true;

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                goalPaused    = false;
                resetAllPositions();   
                GameScore.ajouterButGauche();
            }
        }, 2f);
    }
    
    public static void onRightGoalScored() {
        if (goalPaused) return;            
        goalPaused = true;

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                goalPaused    = false;
                resetAllPositions();  
                GameScore.ajouterButDroite();
            }
        }, 2f);
    }

    private static void resetAllPositions() {
    	Entity old = Registry.getMap().remove("Ball");
        if (old != null) {
            if (old.getBody() != null) {
                PhysicsWorld.getWorld().destroyBody(old.getBody());
            }
        }
        Registry.add(new Ball( 0,   0, 15), "Ball");
        Control.leftTeam.resetPlayers();
        Control.rightTeam.resetPlayers();
    }

}
