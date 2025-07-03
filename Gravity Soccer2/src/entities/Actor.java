package entities;

import java.util.Collection;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import engine.Control;
import entities.behavior.Behavior;

public interface Actor {
	
    void render(Control control);
    void batch(Control control);
    
    public Body getBody();
    public void setBody(Body body);

	float getPosX();
	float getPosY();

	public <T extends Behavior> void addBehavior(Class<T> categoryType, T behaviour);

	public Collection<Behavior> getBehaviors();

	public <T extends Behavior> void removeBehavior(Class<T> categoryType);

	Vector2 getPos();

	

}
