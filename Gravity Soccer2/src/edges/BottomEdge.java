package edges;

import entities.behavior.collision.BottomTouchBehavior;

public class BottomEdge extends Edges{

	public BottomEdge(int x, int y, int w, int h) {
		super(x, y, w, h);
		this.addBehavior(BottomTouchBehavior.class, new BottomTouchBehavior());
	}

}
