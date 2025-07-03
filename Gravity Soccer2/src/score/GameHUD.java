 package score;
 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;

import engine.Control;
import entities.Entity;
import entities.cameras.FreeCamera;
 
 public class GameHUD extends Entity{
 
     Texture hudTexture = new Texture(Gdx.files.internal("hud.png"));
     
     public GameHUD() {
 
         GameScore.setFontScale(1.2f);
     }
 
     public void render(Control control) {
    	 
     }
      
     @Override
 	 public void batch(Control control) {
    	 control.batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    	 control.batch.draw(hudTexture, 20, Gdx.graphics.getHeight() - 120);
         new TimerBar(control.batch).batch(control);
         new LeftPanel(control.batch).batch(control);
         new RightPanel(control.batch).batch(control);
         control.batch.setProjectionMatrix(FreeCamera.getCamera().combined);
 	 }
     
     public void dispose() {
     }

 }
