package score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;

public class GameScore extends Entity{

	private BitmapFont font;
	private SpriteBatch batch;
	private static int scoreEquipeGauche;
    private static int scoreEquipeDroite;

    public static void ajouterButGauche() {
        scoreEquipeGauche++;
    }

    public static void ajouterButDroite() {
        scoreEquipeDroite++;
    }

    public static int getScoreGauche() {
        return scoreEquipeGauche;
    }

    public static int getScoreDroite() {
        return scoreEquipeDroite;
    }

    public static void reset() {
        scoreEquipeGauche = 0;
        scoreEquipeDroite = 0;
    }
	
	public GameScore() {
        super();
        
        font = new BitmapFont(); // Ou charge un .fnt custom
		font.getData().setScale(2f); // Pour rendre le texte plus visible
		batch = new SpriteBatch();
    }

	@Override
	public void render(Control control) {
		for(Behavior behavior : this.getBehaviors()) {
			behavior.update(control, this);
		}
		
		batch.begin();
	    font.draw(batch, "Gauche: " + getScoreGauche(), 50, Gdx.graphics.getHeight() - 20);
	    font.draw(batch, "Droite: " + getScoreDroite(), Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 20);
	    batch.end();
	}

}