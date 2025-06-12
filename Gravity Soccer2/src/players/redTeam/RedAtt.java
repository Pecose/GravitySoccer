package players.redTeam;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import engine.Control;
import entities.behavior.gravity.GravityBehavior;
import entities.behavior.gravity.RedGravityBehavior;
import entities.behavior.velocity.AttVelocityLimitBehavior;
import entities.behavior.velocity.VelocityLimitBehavior;
import players.Player;

public class RedAtt extends Player {

    public RedAtt(int x, int y, int radius) {
        super(x, y, radius);
        this.addBehavior(GravityBehavior.class, new RedGravityBehavior());
        this.addBehavior(VelocityLimitBehavior.class, new AttVelocityLimitBehavior());
    }

    public void render(Control control) {
    	super.render(control);
        
    	control.renderer.begin(ShapeRenderer.ShapeType.Filled);
    	
        control.renderer.setColor(Color.RED);
        control.renderer.circle(this.getPosX(), this.getPosY(), this.getSize());
        
        control.renderer.end();
    }

}
