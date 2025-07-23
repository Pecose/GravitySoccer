package goal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import engine.Control;
import entities.Entity;
import entities.FixtureManager;
import entities.behavior.collision.bodys.CollisionBits;
import entities.world.PhysicsWorld;
import players.country.Team;
import players.side.leftTeam.LeftTeam;

public class Goal extends Entity {

    private GoalNet filetFond, filetGauche, filetDroit;
    private Team team;
    private GoalZone zone;
    private Texture playerTexture;
    private FixtureManager fixtureManager; 

    private static final float CAGE_WIDTH  = 40f;
    private static final float CAGE_HEIGHT = 170f;
    
    public FixtureManager getFixtureManager() { return this.fixtureManager; }
    public GoalZone getZone() { return zone; }
    public Team getTeam() { return team; }

    public Goal(Team team) {
        this.team = team;
        
        // determine position and offset based on team side
        float fieldWidth = Gdx.graphics.getWidth();
        float zoneX;
        float offset;
        if (team.getSideTeam() instanceof LeftTeam) {
            zoneX = -(fieldWidth / 2f);
            offset = 40f;
        } else {
            zoneX = (fieldWidth / 2f) - CAGE_WIDTH;
            offset = 3f;
        }
        float zoneY = -(CAGE_HEIGHT / 2f);

        // create goal zone and fixture
        zone = new GoalZone(zoneX, zoneY, CAGE_WIDTH, CAGE_HEIGHT, this);

        // ✅ on instancie le FixtureManager pour ce body
        fixtureManager = new FixtureManager(zone.getBody());
        createGoalFixture(true);

        // create nets
        filetFond   = new GoalNet(zoneX + CAGE_WIDTH - offset, zoneY,        3, CAGE_HEIGHT);
        filetGauche = new GoalNet(zoneX,                  zoneY,        CAGE_WIDTH, 3);
        filetDroit  = new GoalNet(zoneX,                  zoneY + CAGE_HEIGHT, CAGE_WIDTH, 3);

        playerTexture = new Texture(Gdx.files.internal("images/num/11.png"));
    }

    // ✅ nouvelle méthode qui crée la fixture via le FixtureManager
    private void createGoalFixture(boolean sensor) {
        float halfW = (CAGE_WIDTH  * 0.5f) / PhysicsWorld.PPM;
        float halfH = (CAGE_HEIGHT * 0.5f) / PhysicsWorld.PPM;

        Vector2 center = new Vector2(halfW, halfH);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(halfW, halfH, center, 0f);

        FixtureDef fd = new FixtureDef();
        fd.shape            = shape;
        fd.isSensor         = sensor;
        fd.filter.categoryBits = CollisionBits.CATEGORY_GOALZONE;
        fd.filter.maskBits     = CollisionBits.CATEGORY_BALL;

        // ✅ utilisation du FixtureManager
        fixtureManager.setFixture("goalzone", fd);

        // tu peux aussi directement mettre ton userData
        Fixture created = fixtureManager.getFixture("goalzone");
        if (created != null) created.setUserData(zone);
    }

    @Override
    public void render(Control control) {
        zone.render(control);
        filetFond.render(control);
        filetGauche.render(control);
        filetDroit.render(control);
    }

    @Override
    public void batch(Control control) {
    	zone.batch(control);
        filetFond.batch(control);
        filetGauche.batch(control);
        filetDroit.batch(control);
    }

    @Override
    public void dispose() {
        playerTexture.dispose();
        // ✅ destruction des fixtures via le manager
        if (fixtureManager != null) {
            fixtureManager.clearAll();
        }
    }
    
}
