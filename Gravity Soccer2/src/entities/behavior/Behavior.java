package entities.behavior;

import engine.Control;
import entities.Entity;

public interface Behavior {

	public void update(Control control, Entity entity);
}
