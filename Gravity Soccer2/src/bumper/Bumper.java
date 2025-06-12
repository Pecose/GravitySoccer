package bumper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.behavior.collision.CollisionBumperBehavior;

public class Bumper extends Entity {

    public Bumper(int x1, int y1, int x2, int y2, int x3, int y3) {
        super(x1, y1, x2, y2, x3, y3);

        this.addBehavior(CollisionBumperBehavior.class, new CollisionBumperBehavior());
    }

    @Override
    public void render(Control control) {

        for (Behavior behavior : this.getBehaviors()) {
            behavior.update(control, this);
        }

        control.renderer.begin(ShapeRenderer.ShapeType.Line);

        control.renderer.setColor(Color.YELLOW);

        control.renderer.triangle(this.getX1(), this.getY1(), this.getX2(), this.getY2(), this.getX3(), this.getY3());

        control.renderer.end();
    }
}
