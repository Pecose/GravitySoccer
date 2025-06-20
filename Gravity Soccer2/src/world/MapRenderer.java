package world;

import com.badlogic.gdx.graphics.OrthographicCamera;

import engine.Control;
import entities.cameras.FreeCamera;

public interface MapRenderer {
	
	public void update(FreeCamera camera, Control control);
	public void render(Control control);
	public void dispose();

}