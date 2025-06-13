package goal;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;

public class GoalZone extends Entity {

    private boolean visible = false;
    private Color visibleColor = Color.WHITE; 
    private Color notVisibleColor = new Color(1, 1, 1, 0.3f); 

    public GoalZone(float x, float y, float width, float height, Color color) {
        super(x, y, width, height); 
        this.visibleColor = color;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void render(Control control) {

    	for(Behavior behavior : this.getBehaviors()) {
			behavior.update(control, this);
		}
    	
    	Gdx.gl.glEnable(GL20.GL_BLEND);
    	ShapeRenderer shapeRenderer = control.renderer;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (visible) {
        	shapeRenderer.setColor(visibleColor);
        } else {
        	shapeRenderer.setColor(notVisibleColor);
        }
        shapeRenderer.rect(super.getPosX(), super.getPosY(), super.getWidth(), super.getHeight());
        shapeRenderer.end();
    	
    }
}

