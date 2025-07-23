package goal;

import com.badlogic.gdx.graphics.Color;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.behavior.collision.edges.CollisionEdgeBehavior;

public class GoalNet extends Entity {

    private Color color = Color.WHITE;

    public GoalNet(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.addBehavior(CollisionEdgeBehavior.class, new CollisionEdgeBehavior());
    }

    @Override
    public void render(Control control) {
    	
    	for(Behavior behavior : this.getBehaviors()) {
			behavior.render(control, this);
		}
    	
    	
    	control.renderer.setColor(color);
    	control.renderer.rect(getPosX(), getPosY(), super.getWidth(), super.getHeight());
    	
    }

	@Override
	public void batch(Control control) {}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
