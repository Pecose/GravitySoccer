package players.blueTeam;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import engine.Control;
import entities.behavior.gravity.BlueGravityBehavior;
import entities.behavior.gravity.GravityBehavior;
import entities.behavior.velocity.AttVelocityLimitBehavior;
import entities.behavior.velocity.VelocityLimitBehavior;
import players.Player;

public class BlueAtt extends Player {

    public BlueAtt(int x, int y, int radius) {
        super(x, y, radius);
        this.addBehavior(GravityBehavior.class, new BlueGravityBehavior());
        this.addBehavior(VelocityLimitBehavior.class, new AttVelocityLimitBehavior());
    }

    public void render(Control control) {
    	super.render(control);
        
    	control.renderer.begin(ShapeRenderer.ShapeType.Filled);
    	
        control.renderer.setColor(Color.BLUE);
        control.renderer.circle(this.getPosX(), this.getPosY(), this.getSize());
        
        control.renderer.end();
    }

}
