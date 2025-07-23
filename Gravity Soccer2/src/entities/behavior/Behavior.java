package entities.behavior;

import engine.Control;
import entities.Entity;

public interface Behavior {

	public void render(Control control, Entity entity);
	public void batch(Control control, Entity entity);
}
