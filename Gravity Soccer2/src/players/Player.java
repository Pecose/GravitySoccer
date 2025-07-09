package players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.behavior.collision.bodys.CollisionBodyBehavior;
import players.side.SideTeam;

public class Player extends Entity{

	private SideTeam team;
	protected static Texture shadowTexture;
	
	public Player(int x, int y, int radius, SideTeam team) {
        super(x, y, radius);
        this.team = team;
        this.addBehavior(CollisionBodyBehavior.class, new CollisionBodyBehavior());
        if (shadowTexture == null) {
            shadowTexture = new Texture(Gdx.files.internal("images/shadow.png"));
        }
    }

	@Override
	public void render(Control control) {
		for(Behavior behavior : this.getBehaviors()) {
			behavior.update(control, this);
		}
	}

	public SideTeam getSide() {
		return this.team;
	}
	
	@Override
	public void batch(Control control) {}

}
