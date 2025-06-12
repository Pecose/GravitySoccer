package score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;

public class GameTimer extends Entity {

    private BitmapFont font;
    public static float time = 90f; // 90 secondes par défaut

    public GameTimer() {
        super();
        font = new BitmapFont();
        font.getData().setScale(2f);
    }

    @Override
    public void render(Control control) {
        for (Behavior behavior : this.getBehaviors()) {
            behavior.update(control, this);
        }

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