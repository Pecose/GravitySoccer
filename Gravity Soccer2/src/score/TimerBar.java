package score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import engine.Control;

public class TimerBar {
	
	private BitmapFont font;
	private SpriteBatch batch;
	
	private static final float BOX_X = 20;
    private static final float BOX_Y = Gdx.graphics.getHeight() - 120; 
    private static final float BOX_WIDTH = 120;
    
	public TimerBar(SpriteBatch batch) {
		this.font = new BitmapFont();
		font.getData().setScale(1.2f);
		this.batch = batch;
	}
	
	public void render(Control control) {
		GameTimer timer = new GameTimer();
		timer.render(control); 
		GlyphLayout layout = new GlyphLayout(font, timer.getFormattedTime());
        float textX = BOX_X + (BOX_WIDTH - layout.width) / 2f;
        float textY = BOX_Y + (175 + layout.height) / 2f;
        font.draw(batch, layout, textX, textY);
	}

}
