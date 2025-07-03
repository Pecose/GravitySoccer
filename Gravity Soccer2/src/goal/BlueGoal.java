package goal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import engine.Control;
import entities.Entity;

public class BlueGoal extends Entity {

    private GoalZone zone;
    public GoalZone getZone() {
		return zone;
	}

	public void setZone(GoalZone zone) {
		this.zone = zone;
	}

	private GoalNet filetFond, filetGauche, filetDroit;

    public BlueGoal() {
        super();

        float fieldWidth = Gdx.graphics.getWidth();

        float cageWidth = 40;
        float cageHeight = 170;

        // Position de la cage (collée à gauche, centrée verticalement)
        float zoneX = - (fieldWidth / 2);
        float zoneY = - (cageHeight / 2);

        // Zone intérieure
        zone = new GoalZone(zoneX, zoneY, cageWidth, cageHeight, Color.BLUE);

        // Filet du fond (haut du rectangle, côté gauche)
        filetFond = new GoalNet( zoneX, zoneY, 3, cageHeight );

        // Filet gauche (bord du terrain)
        filetGauche = new GoalNet( zoneX, zoneY, cageWidth, 3 );

        // Filet droit (haut de la cage)
        filetDroit = new GoalNet( zoneX, zoneY + cageHeight, cageWidth, 3 );
    }

    @Override
    public void render(Control control) {
        zone.render(control);
        filetFond.render(control);
        filetGauche.render(control);
        filetDroit.render(control);
    }

	@Override
	public void batch(Control control) {
		// TODO Auto-generated method stub
		
	}
}