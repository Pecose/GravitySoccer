package players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.behavior.collision.ball.BallLastTouchedBehavior;
import entities.behavior.collision.ball.CollisionBallBehavior;
import players.side.SideTeam;

public class Ball extends Entity{

	private SideTeam lastTeamTouched;
	private Color color = Color.WHITE;
	protected static Texture shadowTexture;
	
	public Ball(int x, int y, int radius) {
        super(x, y, radius);
        
        this.addBehavior(CollisionBallBehavior.class, new CollisionBallBehavior());
        this.addBehavior(BallLastTouchedBehavior.class, new BallLastTouchedBehavior());
        
        if (shadowTexture == null) {
            shadowTexture = new Texture(Gdx.files.internal("shadowBall.png"));
        }
    }

	@Override
	public void render(Control control) {
		for(Behavior behavior : this.getBehaviors()) {
			behavior.update(control, this);
		}
		
        control.renderer.setColor(color);
        control.renderer.circle(this.getPosX(), this.getPosY(), this.getSize());
        
	}
	
	@Override
    public void batch(Control control) {
    	control.batch.draw( shadowTexture, this.getPosX() - this.getSize()-1, this.getPosY() - this.getSize()-1 );
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