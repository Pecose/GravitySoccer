package score;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;

public class GameScore extends Entity{

	private static BitmapFont font = new BitmapFont();
	private static int scoreleftTeam;
    private static int scoreRightTeam;

    public static BitmapFont getFont() {
		return font;
	}

	public static void setFontScale(float scale) {
		font.getData().setScale(scale);
	}
	
    public static void ajouterButGauche() {
    	scoreleftTeam++;
    }

    public static void ajouterButDroite() {
    	scoreRightTeam++;
    }

    public static int getLeftScore() {
        return scoreleftTeam;
    }

    public static int getRightScore() {
        return scoreRightTeam;
    }

    public static void reset() {
    	scoreleftTeam = 0;
    	scoreRightTeam = 0;
    }
	
	@Override
	public void render(Control control) {
		for(Behavior behavior : this.getBehaviors()) {
			behavior.update(control, this);
		}
	}

	@Override
	public void batch(Control control) {
		// TODO Auto-generated method stub
		
	}

}