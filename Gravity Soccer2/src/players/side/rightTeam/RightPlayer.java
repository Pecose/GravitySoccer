package players.side.rightTeam;

import engine.Control;
import entities.behavior.gravity.GravityBehavior;
import entities.behavior.gravity.RedGravityBehavior;
import entities.behavior.velocity.DefVelocityLimitBehavior;
import entities.behavior.velocity.VelocityLimitBehavior;
import players.Player;
import players.country.Team;
import players.side.SideTeam;

public class RightPlayer extends Player {

    public RightPlayer(int x, int y, String num, Team team, SideTeam side) {
        super(x, y, num, team, side);
        this.addBehavior(GravityBehavior.class, new RedGravityBehavior());
        this.addBehavior(VelocityLimitBehavior.class, new DefVelocityLimitBehavior());
        
    }
    
    @Override
    public void render(Control control) {
    	super.render(control);
    	
    	control.renderer.setColor(super.getTeam().getColor());
        control.renderer.circle(this.getPosX(), this.getPosY(), this.getSize());
    }

    @Override
    public void batch(Control control) {
    	super.batch(control);
    }
    
    @Override
    public void dispose() {
        super.dispose();
    }

}
