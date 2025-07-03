package entities.behavior.collision.edges;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.behavior.collision.bodys.CollisionBits;
import entities.behavior.collision.bodys.CollisionReactive;
import entities.world.PhysicsWorld;
import players.Ball;
import players.Player;
import players.side.SideTeam;
import players.side.leftTeam.LeftTeam;

public class CollisionEdgeBehavior implements Behavior, CollisionReactive {
    private boolean initialized = false;

    @Override
    public void update(Control control, Entity entity) {
        if (initialized) return;
        Body body = entity.getBody();
        if (body == null) return;

        // 1) calcul des demi-extents en mètres
        float halfW = (entity.getWidth()  / 2f) / PhysicsWorld.PPM;
        float halfH = (entity.getHeight() / 2f) / PhysicsWorld.PPM;

        // 2) création de la shape
        PolygonShape shape = new PolygonShape();
        Vector2 center = new Vector2(halfW, halfH);
        shape.setAsBox(halfW, halfH, center, 0f);

        // 3) définition de la fixture avec filtre
        FixtureDef fd = new FixtureDef();
        fd.shape       = shape;
        fd.density     = 0f;
        fd.restitution = 0f;
        fd.friction    = 0f;

        // ** ICI on déclare le bord et ses cibles de collision : **
        fd.filter.categoryBits = CollisionBits.CATEGORY_EDGES;
        fd.filter.maskBits     = CollisionBits.CATEGORY_BALL 
                               | CollisionBits.CATEGORY_PLAYER;

        body.createFixture(fd);
        shape.dispose();
        initialized = true;
    }

    @Override
    public void onCollision(Entity self, Entity other) {
        // 1) On ne traite QUE Player ou Ball
        if (!(other instanceof Player) && !(other instanceof Ball)) return;

        Body body = other.getBody();
        if (body == null || body.getType() != BodyDef.BodyType.DynamicBody) return;

        
        
        // 2) Choix de la vitesse
        float speed = (other instanceof Ball) ? 3f : 2f;

        // 3) On récupère la team du ballon si c'est une balle, 
        //    sinon on cible peut-être le centre ou un autre joueur
        Vector2 targetPos;
        if (other instanceof Ball) {
        	float currentSpeed = body.getLinearVelocity().len();
            if (currentSpeed >= 3) { return;  }
            Ball ball = (Ball) other;
            SideTeam last = ball.getLastTeamTouched();
            // On part du principe que Control.leftTeam et rightTeam sont initialisés
            Player nearest = (last instanceof LeftTeam)
                ? Control.leftTeam.getNearestPlayer(body.getPosition())
                : Control.rightTeam.getNearestPlayer(body.getPosition());
            if (nearest == null) return;
            targetPos = nearest.getBody().getPosition();
        } else {
        	targetPos = new Vector2(0, 0);
        }

        // 4) Calcul de la direction normalisée
        Vector2 dir = targetPos.cpy().sub(body.getPosition()).nor();

        // 5) Application d'une vitesse constante
        body.setLinearVelocity(dir.scl(speed));
    }

}
