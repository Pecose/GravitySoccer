package players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.behavior.collision.ball.BallLastTouchedBehavior;
import entities.behavior.collision.ball.CollisionBallBehavior;
import entities.behavior.flame.FlameTrailBehavior;
import players.side.SideTeam;

public class Ball extends Entity {

    private SideTeam lastTeamTouched;
    private Color color = Color.WHITE;

    // Shadow (existant)
    protected static Texture shadowTexture;

    // Nouveaux pour le pavage hexagonal
    private static Texture hexTex;
    private static ShaderProgram circleShader;

    public Ball(int x, int y, int radius) {
        super(x, y, radius);

        // 1) Comportements existants
        this.addBehavior(CollisionBallBehavior.class, new CollisionBallBehavior());
        this.addBehavior(BallLastTouchedBehavior.class, new BallLastTouchedBehavior());
        this.addBehavior(FlameTrailBehavior.class, new FlameTrailBehavior());

        // 2) Shadow existant
        if (shadowTexture == null) {
            shadowTexture = new Texture(Gdx.files.internal("images/shadowBall.png"));
        }

        // 3) Texture hexagonale en Repeat
        if (hexTex == null) {
            hexTex = new Texture(Gdx.files.internal("images/hexa.png"));
            hexTex.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        }

        // 4) Shader pour masquer en cercle
        if (circleShader == null) {
            ShaderProgram.pedantic = false;
            circleShader = new ShaderProgram(
                Gdx.files.internal("shaders/circle.vert"),
                Gdx.files.internal("shaders/circle.frag")
            );
            if (!circleShader.isCompiled()) {
                throw new RuntimeException("Erreur shader : " + circleShader.getLog());
            }
        }
    }

    @Override
    public void render(Control control) {
        // Mise à jour des comportements (inchangé)
        for (Behavior behavior : this.getBehaviors()) {
            behavior.render(control, this);
        }
        // On supprime le dessin au ShapeRenderer pour ne tout faire en batch()
    }

    @Override
    public void batch(Control control) {
        SpriteBatch batch = (SpriteBatch)control.batch;

        // 1) on vide tout pour partir sur une base propre
        batch.flush();

        // 2) on bind notre shader et on dessine la balle texturée
        batch.setShader(circleShader);
        circleShader.bind();
        circleShader.setUniformi("u_texture", 0);
        circleShader.setUniformf("u_tileSize", hexTex.getWidth(), hexTex.getHeight());
        circleShader.setUniformf("u_ballCenter", getPosX(), getPosY());
        circleShader.setUniformf("u_radius", getSize());
        circleShader.setUniformi("u_circleMask", 1);

        float d = getSize() * 2f;
        batch.draw(
            hexTex,
            getPosX() - getSize(),
            getPosY() - getSize(),
            d, d
        );

        // 3) on reset le shader
        batch.flush();
        batch.setShader(null);

        // 4) on dessine l’ombre **après** la balle texturée
        batch.draw(
            shadowTexture,
            getPosX() - getSize() - 1,
            getPosY() - getSize() - 1
        );
    }


    // --- Getters / Setters existants ---

    public SideTeam getLastTeamTouched() {
        return lastTeamTouched;
    }

    public void setLastTeamTouched(SideTeam side) {
        this.lastTeamTouched = side;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void dispose() {
        super.dispose();
        // attention : static textures/shaders, à libérer quand l’app se ferme
        if (hexTex != null)   hexTex.dispose();
        if (circleShader != null) circleShader.dispose();
        if (shadowTexture != null) shadowTexture.dispose();
    }
}
