package entities.video;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GoalCelebrationPlayer {
    void play(String path, Runnable onComplete);
    void updateAndRender(SpriteBatch batch);
    boolean isPlaying();
    void stop();   // pour skip si besoin
}
