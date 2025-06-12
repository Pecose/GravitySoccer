package engine.manager;

import com.badlogic.gdx.math.Vector2;

import entities.cameras.FreeCamera;
import world.MapRendererObject;

public interface ObjectGeneratorInterface {

	float newRotation(float angle);

	Vector2 newVelocity(float x, float y);

	float newSize(float x);

	Vector2 newPosition(float x, float y);

	FreeCamera newFreeCamera();

	MapRendererObject newMapRenderer();
}
