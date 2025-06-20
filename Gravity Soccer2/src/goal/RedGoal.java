package goal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import engine.Control;
import entities.Entity;


public class RedGoal extends Entity {

    private GoalZone zone;
    
    public GoalZone getZone() {
		return zone;
	}

	public void setZone(GoalZone zone) {
		this.zone = zone;
	}

	private GoalNet filetFond, filetGauche, filetDroit;

    public RedGoal() {
        super();

        float fieldWidth = Gdx.graphics.getWidth();

        float cageWidth = 40f;
        float cageHeight = 170f;

        float zoneX = (fieldWidth / 2) - cageWidth; 
        float zoneY = - (cageHeight / 2);          

        // Zone intérieure
        zone = new GoalZone(zoneX, zoneY, cageWidth, cageHeight, Color.RED);

        // Filet du fond (haut du rectangle)
        filetFond = new GoalNet(
        	zoneX + cageWidth - 3, zoneY,
            3, cageHeight
        );

        // Filet gauche
        filetGauche = new GoalNet(
            zoneX, zoneY,
            cageWidth, 3
        );

        // Filet droit (côté bord du terrain)
        filetDroit = new GoalNet(
            zoneX, zoneY + cageHeight,
            cageWidth, 3
        );
      
    }

    @Override
    public void render(Control control) {
        zone.render(control);
        filetFond.render(control);
        filetGauche.render(control);
        filetDroit.render(control);
    }
}

