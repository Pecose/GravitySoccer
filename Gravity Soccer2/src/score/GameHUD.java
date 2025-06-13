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
 
     private static final float BOX_X = 10;
     private static final float BOX_Y = Gdx.graphics.getHeight() - 120; 
     private static final float BOX_WIDTH = 200;
     private static final float BOX_HEIGHT = 100;
     private static final float SCORE_BOX_SIZE = 24f;
 
     private ShapeRenderer shapeRenderer;
     private SpriteBatch batch;
     private BitmapFont font;
 
     private GameScore score;
     private GameTimer timer;
 
     private String teamLeftAbbr, teamRightAbbr;
     private Texture flagLeft, flagRight;
 
     public GameHUD() {
         this.score = new GameScore();
         this.timer = new GameTimer();
         this.teamLeftAbbr = "RED";
         this.teamRightAbbr = "BLU";
         this.flagLeft = new Texture("flags/FRA.png");;
         this.flagRight = new Texture("flags/FRA.png");;
 
         shapeRenderer = new ShapeRenderer();
         batch = new SpriteBatch();
         font = new BitmapFont();
         font.getData().setScale(1.2f);
     }
 
     public void render(Control control) {
         // Mets à jour le timer si tu veux qu'il soit lié au HUD
         timer.render(control); // ou timer.update(delta) ailleurs
 
        // 1) Fond semi-transparent et boîtes de score
         shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
         shapeRenderer.setColor(0, 0, 0, 0.6f);
         shapeRenderer.rect(BOX_X, BOX_Y, BOX_WIDTH, BOX_HEIGHT);

         // Dessine les boîtes pour le score
         shapeRenderer.setColor(0.1f, 0.1f, 0.1f, 0.8f);
         float colX = BOX_X + 10;
         float col2X = BOX_X + BOX_WIDTH / 2f + 10;
         float scoreBoxY = BOX_Y + 10;
         shapeRenderer.rect(colX, scoreBoxY, SCORE_BOX_SIZE, SCORE_BOX_SIZE);
         shapeRenderer.rect(col2X, scoreBoxY, SCORE_BOX_SIZE, SCORE_BOX_SIZE);
         shapeRenderer.end();
 
         String time = timer.getFormattedTime();
         GlyphLayout layout = new GlyphLayout(font, time);
         float textX = BOX_X + (BOX_WIDTH - layout.width) / 2f;
         
         // 2) Texte et drapeaux
         batch.begin();
         // Timer en haut-centre de la boîte
         
         font.draw(batch, layout, textX, BOX_Y + BOX_HEIGHT - 10);
 
         // Position de la première colonne
         colX = BOX_X + 10;
         float rowY = BOX_Y + BOX_HEIGHT - 35;
 
         // Colonne gauche
         font.draw(batch, teamLeftAbbr, colX, rowY);
         batch.draw(flagLeft, colX, rowY - 20, 40, 8);    // drapeau mince
         layout.setText(font, String.valueOf(GameScore.getScoreGauche()));
         float scoreTextX = colX + (SCORE_BOX_SIZE - layout.width) / 2f;
         float scoreTextY = scoreBoxY + (SCORE_BOX_SIZE + layout.height) / 2f;
         font.draw(batch, layout, scoreTextX, scoreTextY);
 
         // Colonne droite
         col2X = BOX_X + BOX_WIDTH/2f + 10;
         font.draw(batch, teamRightAbbr, col2X, rowY);
         batch.draw(flagRight, col2X, rowY - 20, 40, 8);
         layout.setText(font, String.valueOf(GameScore.getScoreDroite()));
         scoreTextX = col2X + (SCORE_BOX_SIZE - layout.width) / 2f;
         scoreTextY = scoreBoxY + (SCORE_BOX_SIZE + layout.height) / 2f;
         font.draw(batch, layout, scoreTextX, scoreTextY);
 
         batch.end();
     }
 
     public void dispose() {
         shapeRenderer.dispose();
         batch.dispose();
         font.dispose();
         // flags généralement dispo ailleurs
     }
 }
