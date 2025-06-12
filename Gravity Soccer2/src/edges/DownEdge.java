package edges;

import entities.behavior.collision.DownTouchBehavior;

public class DownEdge extends Edges{

	public DownEdge(int x, int y, int w, int h) {
		super(x, y, w, h);
		this.addBehavior(DownTouchBehavior.class, new DownTouchBehavior());
	}

}
