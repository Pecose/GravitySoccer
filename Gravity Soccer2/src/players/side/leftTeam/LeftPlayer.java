package players.side.leftTeam;

import engine.Control;
import entities.behavior.gravity.BlueGravityBehavior;
import entities.behavior.gravity.GravityBehavior;
import entities.behavior.velocity.DefVelocityLimitBehavior;
import entities.behavior.velocity.VelocityLimitBehavior;
import players.Player;
import players.country.Team;
import players.side.SideTeam;

public class LeftPlayer extends Player {

    public LeftPlayer(int x, int y, String num, Team team, SideTeam side) {
        super(x, y, num, team, side);
        this.addBehavior(GravityBehavior.class, new BlueGravityBehavior());
        this.addBehavior(VelocityLimitBehavior.class, new DefVelocityLimitBehavior());
        
    }

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
