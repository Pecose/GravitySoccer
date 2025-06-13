package goal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import engine.Control;
import entities.Entity;

public class BlueGoal extends Entity {

    private GoalZone zone;
    private GoalNet filetFond, filetGauche, filetDroit;

    public BlueGoal() {
        super();

        float fieldWidth = Gdx.graphics.getWidth();

        float cageWidth = 40f;
        float cageHeight = 170f;

        // Position de la cage (collée à gauche, centrée verticalement)
        float zoneX = - (fieldWidth / 2);
        float zoneY = - (cageHeight / 2);

        // Zone intérieure
        zone = new GoalZone(zoneX, zoneY, cageWidth, cageHeight, Color.BLUE);

        // Filet du fond (haut du rectangle, côté gauche)
        filetFond = new GoalNet(
            zoneX, zoneY,
            3, cageHeight
        );

        // Filet gauche (bord du terrain)
        filetGauche = new GoalNet(
            zoneX, zoneY,
            cageWidth, 3
        );

        // Filet droit (haut de la cage)
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