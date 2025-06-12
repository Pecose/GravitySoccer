package engine.manager;

import com.badlogic.gdx.math.Vector2;

import entities.cameras.FreeCamera;
import entities.cameras.FreeCameraObject;
import world.MapRendererObject;

public class ObjectGenerator implements ObjectGeneratorInterface {
	
	@Override
	public MapRendererObject newMapRenderer() {
		return new MapRendererObject();
	}
	
	@Override
	public Vector2 newPosition(float x, float y) {
		return new Vector2(x, y);
	}
	
	@Override
	public float newSize(float x) {
		return x;
	}
	
	@Override
	public Vector2 newVelocity(float x, float y) {
		return new Vector2(x, y);
	}
	
	@Override
	public float newRotation(float angle) {
		return angle;
	}
	
	@Override
	public FreeCamera newFreeCamera() {
		return new FreeCameraObject();
	}
	
}
