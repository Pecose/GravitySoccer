package entities.cameras;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.behavior.cameraBehavior.CameraMovementBehavior;
import entities.behavior.cameraBehavior.CameraMovementComponent;
import entities.behavior.cameraBehavior.CameraZoomBehavior;
import entities.behavior.cameraBehavior.CameraZoomComponent;

public class FreeCameraObject extends Entity implements FreeCamera{
    

    public FreeCameraObject() {
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        addBehavior(CameraZoomBehavior.class, new CameraZoomComponent());
        addBehavior(CameraMovementBehavior.class, new CameraMovementComponent());
    }
    
	@Override
	public void render(Control control) {
		for(Behavior behavior : this.getBehaviors()) {
			behavior.update(control, this);
		}
	}

	@Override
	public void batch(Control control) {
	}
    
}
