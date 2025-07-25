package entities.behavior.collision.goalzone;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import engine.Control;
import entities.Entity;
import goal.GoalZone;
import players.Ball;

public class GoalZoneTouchBehavior implements GoalZoneBehavior {
	
	private static final String TEX_SHADOW = "images/goalShadow.png";
	private static final String TEX_MAIN   = "images/num/11.png";
	private static final String FRAG_PATH   = "shaders/goal.frag";
	private static final String VERT_PATH = "shaders/goal.vert";

	private ShaderProgram shader;
    private Texture texShadow;
    private Texture texMain;

    private boolean resourcesLoaded = false;
	    
    @Override
    public void render(Control control, Entity entity) {
        // Debug visuel : affiche la zone avec la couleur de l'équipe
//    	((GoalZone)entity).getGoal().getFixtureManager().getFixture("goalzone").setSensor(false);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        control.renderer.setColor(((GoalZone)entity).getGoal().getTeam().getColor());
        control.renderer.rect(
            entity.getPosX(),
            entity.getPosY(),
            entity.getWidth(),
            entity.getHeight()
        );
        control.renderer.setColor(Color.WHITE);
    }

    @Override
    public void onCollision(Entity self, Entity other) {
    	 if (other instanceof Ball) {
             Control.soundManager.playNextNote();
        }
    }

    private void loadResources() {
        if (resourcesLoaded) return;

        // 1) Shader
        String fragSrc = Gdx.files.internal(FRAG_PATH).readString();
        String vertSrc = Gdx.files.internal(VERT_PATH).readString();
        shader = new ShaderProgram(vertSrc, fragSrc);
        if (!shader.isCompiled()) {
            throw new IllegalStateException("Shader compile error: " + shader.getLog());
        }

        // 2) Textures
        texShadow = new Texture(Gdx.files.internal(TEX_SHADOW));
        texShadow.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        texMain = new Texture(Gdx.files.internal(TEX_MAIN));
        texMain.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        resourcesLoaded = true;
    }
    
    @Override
    public void batch(Control control, Entity entity) {
    	loadResources(); 

    	SpriteBatch batch = control.batch;
    	float x = entity.getPosX(), y = entity.getPosY();
    	float w = entity.getWidth(), h = entity.getHeight();

    	ShaderProgram prev = batch.getShader();
    	batch.setShader(shader);

    	// -------- Ombre --------
    	shader.setUniformi("u_chroma", 0);
    	shader.setUniformf("u_alphaMul", 1.0f);   // ou 0.8 si tu veux plus sombre
    	batch.draw(texShadow, x, y, w, h);

    	// -------- Numéro 11 (fond vert) --------
    	shader.setUniformi("u_chroma", 1);
    	shader.setUniformf("u_keyColor", 0f, 1f, 0f);
    	shader.setUniformf("u_thresh", 0.6f);
    	shader.setUniformf("u_smooth", 0.1f);
    	shader.setUniformf("u_alphaMul", 1.0f);
    	batch.draw(texMain, x, y, w, h);

    	batch.setShader(prev);

    }
}
