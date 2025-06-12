package entities.behavior.cameraBehavior;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import engine.Control;
import entities.Entity;
import entities.cameras.FreeCamera;

public class CameraZoomComponent implements CameraZoomBehavior {

    private float zoomSpeed = 2f;
    private float minZoom = 0.1f;
    private float maxZoom = 2f;

    @Override
    public void update(Control control, Entity entities) {
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            FreeCamera.camera.zoom += zoomSpeed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
        	FreeCamera.camera.zoom -= zoomSpeed * deltaTime;
        }

        // Clamp zoom
        if (FreeCamera.camera.zoom < minZoom) FreeCamera.camera.zoom = minZoom;
        if (FreeCamera.camera.zoom > maxZoom) FreeCamera.camera.zoom = maxZoom;

        FreeCamera.camera.update();
    }
}
