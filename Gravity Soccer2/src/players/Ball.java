package players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.behavior.collision.BallTouchBumperBehavior;
import entities.behavior.collision.BallTouchEdgesBehavior;
import entities.behavior.collision.BallTouchPlayerBehavior;
import entities.behavior.collision.CollisionBallBehavior;

public class Ball extends Entity{

	public Ball(int x, int y, int radius) {
        super(x, y, radius);
        
        this.addBehavior(CollisionBallBehavior.class, new CollisionBallBehavior());
        this.addBehavior(BallTouchPlayerBehavior.class, new BallTouchPlayerBehavior());
        this.addBehavior(BallTouchEdgesBehavior.class, new BallTouchEdgesBehavior());
        this.addBehavior(BallTouchBumperBehavior.class, new BallTouchBumperBehavior());
    }

	@Override
	public void render(Control control) {
		for(Behavior behavior : this.getBehaviors()) {
			behavior.update(control, this);
		}
		
		control.renderer.begin(ShapeRenderer.ShapeType.Filled);
    	
        control.renderer.setColor(Color.WHITE);
        control.renderer.circle(this.getPosX(), this.getPosY(), this.getSize());
        
        control.renderer.end();
	}

}