package entities;

import java.util.Collection;

import com.badlogic.gdx.physics.box2d.Body;

import engine.Control;
import entities.behavior.Behavior;

public interface Actor {
	
    void render(Control control);

    public Body getBody();
    public void setBody(Body body);

	float getPosX();

	/** Retourne la position Y en pixels, d’après le Body en mètres. */
	float getPosY();

	/** Ne sert plus : on ne stocke plus de position séparée. */
	Actor setPosX(float x);

	Actor setPosY(float y);

	public <T extends Behavior> void addBehavior(Class<T> categoryType, T behaviour);

	public Collection<Behavior> getBehaviors();

	public <T extends Behavior> void removeBehavior(Class<T> categoryType);

}
