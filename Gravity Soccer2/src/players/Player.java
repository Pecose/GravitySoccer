package players;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.behavior.collision.CollisionBodyBehavior;

public class Player extends Entity{

	public Player(int x, int y, int radius) {
        super(x, y, radius);
        
        this.addBehavior(CollisionBodyBehavior.class, new CollisionBodyBehavior());
        
    }

	@Override
	public void render(Control control) {
		for(Behavior behavior : this.getBehaviors()) {
			behavior.update(control, this);
		}
	}

}
