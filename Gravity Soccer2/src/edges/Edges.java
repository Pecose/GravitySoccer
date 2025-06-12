package edges;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.behavior.collision.CollisionEdgeBehavior;

public class Edges extends Entity{

	public Edges(int x, int y, int w, int h) {
        super(x, y, w, h);
        
        this.addBehavior(CollisionEdgeBehavior.class, new CollisionEdgeBehavior());
        
    }

	@Override
	public void render(Control control) {
		for(Behavior behavior : this.getBehaviors()) {
			behavior.update(control, this);
		}
	}

}

