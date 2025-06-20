 package score;
 
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.graphics.Texture;
 import com.badlogic.gdx.graphics.g2d.BitmapFont;
 import com.badlogic.gdx.graphics.g2d.GlyphLayout;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
 
 import engine.Control;
 import entities.Entity;
 
 public class GameHUD extends Entity{
 
     Texture hudTexture = new Texture(Gdx.files.internal("hud.png"));
     
     private ShapeRenderer shapeRenderer;
     private SpriteBatch batch;
     private BitmapFont font;
 
 
     public GameHUD() {
 
         shapeRenderer = new ShapeRenderer();
         batch = new SpriteBatch();
         font = new BitmapFont();
         font.getData().setScale(1.2f);
         GameScore.setFontScale(1.2f);
     }
 
     public void render(Control control) {
         
         
         batch.begin();
         
         batch.draw(hudTexture, 20, Gdx.graphics.getHeight() - 120);
         new TimerBar(batch).render(control);

         new LeftPanel(batch).render(control);
         new RightPanel(batch).render(control);
         
         batch.end();
     }
 
     public void dispose() {
         shapeRenderer.dispose();
         batch.dispose();
         font.dispose();
     }
 }
