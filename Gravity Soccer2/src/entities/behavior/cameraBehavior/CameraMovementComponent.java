package entities.behavior.cameraBehavior;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.cameras.FreeCamera;

public class CameraMovementComponent implements CameraMovementBehavior {

    private float moveSpeed = 1000f;

    @Override
    public void update(Control control, Entity entities) {
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
        	FreeCamera.camera.position.x -= moveSpeed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
        	FreeCamera.camera.position.x += moveSpeed * deltaTime;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
        	FreeCamera.camera.position.y += moveSpeed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
        	FreeCamera.camera.position.y -= moveSpeed * deltaTime;
        }

        FreeCamera.camera.update();
    }
}
