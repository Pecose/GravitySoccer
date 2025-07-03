package score;

import com.badlogic.gdx.Gdx;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;

public class GameTimer {

    public static float time = 90f;


    public void time(Control control) {
        time -= Gdx.graphics.getDeltaTime();
    }
    
    public String getFormattedTime() {
        int seconds = (int) time;
        int centiseconds = (int) ((time - seconds) * 100);
        return String.format("%02d:%02d", seconds, centiseconds);
    }

    public boolean isFinished() {
        return time <= 0;
    }
}