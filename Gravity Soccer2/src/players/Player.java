package players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.behavior.collision.bodys.CollisionBodyBehavior;
import players.country.Team;
import players.side.SideTeam;

public abstract class Player extends Entity {
    private final Team     team;
    private final SideTeam sideTeam;
    private static ShaderProgram circleShader;
    private static Texture shadowTexture;
    private final Texture playerTexture;
    private final String num;

    static {
        ShaderProgram.pedantic = false;
        circleShader = new ShaderProgram(
            Gdx.files.internal("shaders/circle.vert"),
            Gdx.files.internal("shaders/circle.frag")
        );
        if (!circleShader.isCompiled()) {
            throw new RuntimeException("Erreur shader : " + circleShader.getLog());
        }
        shadowTexture = new Texture(Gdx.files.internal("images/shadow.png"));
    }

    public Player(int x, int y, String num, Team team, SideTeam sideTeam) {
        super(x, y, 30);
        this.team     = team;
        this.sideTeam = sideTeam;
        this.num = num;
        this.addBehavior(CollisionBodyBehavior.class, new CollisionBodyBehavior());

        // 3) Maillot = texture repeatable
        String shirt = "images/num/"+ num +".png";
        this.playerTexture = new Texture(Gdx.files.internal(shirt));
        this.playerTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
    }

    @Override
    public void render(Control control) {
        for (Behavior b : getBehaviors()) b.render(control, this);
    }

    @Override
    public void batch(Control control) {
        SpriteBatch batch = (SpriteBatch) control.batch;
        float cx = getPosX(), cy = getPosY(), r = getSize();
        float d  = r * 2f;

        // 1) vide le batch
        batch.flush();

        // 2) active le shader circulaire
        batch.setShader(circleShader);
        circleShader.bind();

        // 3) passe les uniforms (ne pas oublier u_tileSize !)
        circleShader.setUniformi("u_texture",    0);
        circleShader.setUniformf("u_tileSize",   playerTexture.getWidth(), playerTexture.getHeight());
        circleShader.setUniformf("u_ballCenter", cx, cy);
        circleShader.setUniformf("u_radius",     r);
        circleShader.setUniformi("u_circleMask", 1);

        // 4) DESSINE LE QUAD **SOUS SHADER** (c’est lui qui masque en rond + tile + scroll
        batch.draw( playerTexture, cx - r, cy - r, d, d );

        // 5) reset & flush pour revenir au shader normal
        batch.flush();
        batch.setShader(null);

        // 6) dessine l’ombre **après** le shader
        batch.draw( shadowTexture, cx - r - 1, cy - r - 1 );

        // (optionnel) si tu voulais un sprite “normal” en plus, tu le dessinerais ici
        // batch.draw(tonSprite, cx - r, cy - r, d, d);
    }


    @Override
    public void dispose() {
        super.dispose();
        playerTexture.dispose();
    }

    public Team getTeam() { return team; }
    public Texture getShadowTexture() { return shadowTexture; }
    public SideTeam getSideTeam() { return sideTeam; }
	public String getNum() { return num; }
}
