package entities.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public final class PhysicsWorld {
    public static final float PPM = 100f;   // 100 px = 1 m
    private static final World WORLD = new World(new Vector2(0, 0), true);

//    static {
//        createBoundaryWalls();
//        createTopBumper();
//        createBottomBumper();
//    }

    private PhysicsWorld() {}

    public static World get() {
        return WORLD;
    }

//    public static void step(float dt) {
//        WORLD.step(dt, 6, 2);
//    }

    /** Crée deux murs statiques en haut et en bas pour les collisions. */
    private static void createBoundaryWalls() {
        // dimensions du terrain en pixels
        float halfWidthPx  = 960f;   // 1920 / 2
        float halfHeightPx = 540f;   // 1080 / 2

        // on convertit en mètres
        float halfW = halfWidthPx  / PPM;
        float halfH = halfHeightPx / PPM;
        float thickness = 0.1f;      // épaisseur de 10 cm

        // Body statique (positionne au centre)
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set(0, 0);
        Body wallBody = WORLD.createBody(bd);

        // Shape pour le mur du haut (rectangle fin)
        PolygonShape top = new PolygonShape();
        top.setAsBox(
            halfW, thickness,             // demi-largesur, demi-épaisseur
            new Vector2(0,  halfH + thickness),  // centre du top
            0                              // angle
        );
        FixtureDef topFd = new FixtureDef();
        topFd.shape       = top;
        topFd.restitution = 1f;   // rebond parfait
        topFd.friction    = 0f;
        wallBody.createFixture(topFd);
        top.dispose();

        // Shape pour le mur du bas
        PolygonShape bot = new PolygonShape();
        bot.setAsBox(
            halfW, thickness,
            new Vector2(0, -halfH - thickness),
            0
        );
        FixtureDef botFd = new FixtureDef();
        botFd.shape       = bot;
        botFd.restitution = 1f;
        botFd.friction    = 0f;
        wallBody.createFixture(botFd);
        bot.dispose();
    }
    
    private static void createTopBumper() {
        // ta PPM = 100 px = 1 m
        final float radiusPx    = 50f;      // rayon en pixels
        final float halfHeightPx = 540f;     // demi-hauteur du terrain en pixels

        // conversion px → m
        float radiusM = radiusPx / PPM;
        // on place le centre du cercle à y = (demi-hauteur – rayon) pour qu'il
        // « touche » juste la touche (le bord du terrain)
        float yPx = halfHeightPx - radiusPx;
        float yM  = yPx / PPM;

        // création du Body statique
        BodyDef bd = new BodyDef();
        bd.type     = BodyDef.BodyType.StaticBody;
        bd.position.set(0f, yM +0.5f);            // centre X=0, Y = touche-haut
        Body bumper = WORLD.createBody(bd);

        // shape circulaire
        CircleShape shape = new CircleShape();
        shape.setRadius(radiusM);

        // fixture
        FixtureDef fd = new FixtureDef();
        fd.shape       = shape;
        fd.restitution = 1f;
        fd.friction    = 0f;
        bumper.createFixture(fd);
        shape.dispose();
    }
    
    private static void createBottomBumper() {
        // Paramètres identiques à createMiddleBumper, mais y inversé
        final float radiusPx     = 50f;   // même rayon que le bumper du haut
        final float halfHeightPx = 540f;   // demi-hauteur du terrain

        // conversion px → m
        float radiusM = radiusPx / PPM;
        // centre Y en pixels : -demiHauteur + rayon
        float yPx = -halfHeightPx + radiusPx;
        float yM  = yPx / PPM;

        // Body statique centré X=0, Y = yM
        BodyDef bd = new BodyDef();
        bd.type     = BodyDef.BodyType.StaticBody;
        bd.position.set(0f, yM -0.5f);
        Body bumper = WORLD.createBody(bd);

        // Shape & Fixture
        CircleShape shape = new CircleShape();
        shape.setRadius(radiusM);

        FixtureDef fd = new FixtureDef();
        fd.shape       = shape;
        fd.restitution = 1f;
        fd.friction    = 0f;
        bumper.createFixture(fd);
        shape.dispose();
    }
    
    
}
