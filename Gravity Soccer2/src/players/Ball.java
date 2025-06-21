package players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.behavior.ball.BallLastTouchedBehavior;
import entities.behavior.collision.ball.BallTouchBumperBehavior;
import entities.behavior.collision.ball.BallTouchEdgesBehavior;
import entities.behavior.collision.ball.BallTouchGoalZoneBehavior;
import entities.behavior.collision.ball.BallTouchPlayerBehavior;
import entities.behavior.collision.ball.CollisionBallBehavior;
import players.side.SideTeam;

public class Ball extends Entity{

	private SideTeam lastTeamTouched;
	private Color color = Color.WHITE;
	protected static Texture shadowTexture;
	
	public Ball(int x, int y, int radius) {
        super(x, y, radius);
        
        this.addBehavior(CollisionBallBehavior.class, new CollisionBallBehavior());
        this.addBehavior(BallTouchPlayerBehavior.class, new BallTouchPlayerBehavior());
        this.addBehavior(BallTouchEdgesBehavior.class, new BallTouchEdgesBehavior());
        this.addBehavior(BallTouchBumperBehavior.class, new BallTouchBumperBehavior());
        this.addBehavior(BallLastTouchedBehavior.class, new BallLastTouchedBehavior());
        this.addBehavior(BallTouchGoalZoneBehavior.class, new BallTouchGoalZoneBehavior());
        
        if (shadowTexture == null) {
            shadowTexture = new Texture(Gdx.files.internal("shadowBall.png"));
        }
    }

	@Override
	public void render(Control control) {
		for(Behavior behavior : this.getBehaviors()) {
			behavior.update(control, this);
		}
		
		control.renderer.begin(ShapeRenderer.ShapeType.Filled);
    	
        control.renderer.setColor(color);
        control.renderer.circle(this.getPosX(), this.getPosY(), this.getSize());
        
        control.renderer.end();
        
        control.batch.begin();
    	control.batch.draw( shadowTexture, this.getPosX() - this.getSize()-1, this.getPosY() - this.getSize()-1);
    	control.batch.end();
	}

	public SideTeam getLastTeamTouched() {
		return lastTeamTouched;
	}

	public void setLastTeamTouched(SideTeam side) {
		this.lastTeamTouched = side;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}