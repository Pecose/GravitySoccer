package players;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.behavior.collision.bodys.CollisionBodyBehavior;
import players.team.Side;

public class Player extends Entity{

	private Side side = Side.NONE;
	
	public Player(int x, int y, int radius, Side side) {
        super(x, y, radius);
        this.side = side;
        this.addBehavior(CollisionBodyBehavior.class, new CollisionBodyBehavior());
    }

	@Override
	public void render(Control control) {
		for(Behavior behavior : this.getBehaviors()) {
			behavior.update(control, this);
		}
	}

	public Side getSide() {
		return this.side;
	}

}
