package players.side.rightTeam;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import engine.Control;
import entities.behavior.gravity.GravityBehavior;
import entities.behavior.gravity.RedGravityBehavior;
import entities.behavior.velocity.DefVelocityLimitBehavior;
import entities.behavior.velocity.VelocityLimitBehavior;
import players.Player;
import players.side.SideTeam;

public class RightPlayer extends Player {

    public RightPlayer(int x, int y, int radius, SideTeam team) {
        super(x, y, radius*2, team);
        this.addBehavior(GravityBehavior.class, new RedGravityBehavior());
        this.addBehavior(VelocityLimitBehavior.class, new DefVelocityLimitBehavior());
    }

    public void render(Control control) {
    	super.render(control);
        
    	control.renderer.begin(ShapeRenderer.ShapeType.Filled);
    	
        control.renderer.setColor(Color.RED);
        control.renderer.circle(this.getPosX(), this.getPosY(), this.getSize());
        
        control.renderer.end();
        
        control.batch.begin();
    	control.batch.draw( shadowTexture, this.getPosX() - this.getSize()-1, this.getPosY() - this.getSize()-1 );
    	control.batch.end();
    }

}
