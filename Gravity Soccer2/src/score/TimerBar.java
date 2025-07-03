package score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import engine.Control;

public class TimerBar {
	
	private static final float BOX_X = 20;
    private static final float BOX_Y = Gdx.graphics.getHeight() - 120; 
    private static final float BOX_WIDTH = 120;
    
	public TimerBar(SpriteBatch batch) {
		GameScore.getFont().getData().setScale(1.2f);
	}
	
	public void batch(Control control) {
		GameTimer timer = new GameTimer();
		timer.time(control); 
		GlyphLayout layout = new GlyphLayout(GameScore.getFont(), timer.getFormattedTime());
        float textX = BOX_X + (BOX_WIDTH - layout.width) / 2f;
        float textY = BOX_Y + (175 + layout.height) / 2f;
        GameScore.getFont().draw(control.batch, layout, textX, textY);
	}

}
