package entities.behavior.collision.bumper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.behavior.collision.bodys.CollisionBits;
import entities.behavior.collision.bodys.CollisionReactive;
import entities.world.PhysicsWorld;
import players.Ball;

public class CollisionBumperBehavior implements Behavior, CollisionReactive {
    private boolean initialized = false;

    @Override
    public void update(Control control, Entity entity) {
        if (initialized) return;


        Body body = entity.getBody();

        // 🔹 Récupère les 3 sommets du triangle
        Vector2 p1 = new Vector2(entity.getX1(), entity.getY1());
        Vector2 p2 = new Vector2(entity.getX2(), entity.getY2());
        Vector2 p3 = new Vector2(entity.getX3(), entity.getY3());

        // 🔹 Convertit en mètres (pixels → Box2D)
        p1.scl(1f / PhysicsWorld.PPM);
        p2.scl(1f / PhysicsWorld.PPM);
        p3.scl(1f / PhysicsWorld.PPM);

        // 🔹 Calcule le centre du triangle
        Vector2 center = new Vector2(
            (p1.x + p2.x + p3.x) / 3f,
            (p1.y + p2.y + p3.y) / 3f
        );

        // 🔹 Ramène les points autour du centre (local au Body)
        p1.sub(center);
        p2.sub(center);
        p3.sub(center);

        // 🔹 Positionne le Body au centre géométrique
        body.setTransform(center, 0);

        // 🔹 Crée la forme triangulaire
        PolygonShape shape = new PolygonShape();
        shape.set(new Vector2[] { p1, p2, p3 });

        // 🔹 Prépare la fixture
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 0f;
        fd.friction = 0f;
        fd.restitution = 1f; // rebond fort !
        
        fd.filter.categoryBits = CollisionBits.CATEGORY_BUMPER;
        fd.filter.maskBits = CollisionBits.CATEGORY_BALL;

        // 🔹 Ajoute la fixture au Body
        body.createFixture(fd);
        shape.dispose();

        initialized = true;

    }
    @Override
    public void onCollision(Entity self, Entity other) {
        if (!(other instanceof Ball)) return;

        Body ballBody = other.getBody();
        if (ballBody == null) return;

        // 1) trouve la position de la balle et du centre
        Vector2 ballPos  = ballBody.getPosition(); // en mètres
        Vector2 center   = new Vector2(0, 0);

        // 2) direction normalisée vers le centre
        Vector2 dir = center.cpy().sub(ballPos).nor();

        // 3) magnitude de la vitesse (tu peux fixer une valeur ou t'appuyer  
        //    sur l'ancien module de restitution pour la garder dynamique)
        float speed = 10f; 
        // ou float speed = ballBody.getLinearVelocity().len();

        // 4) applique la nouvelle vitesse
        ballBody.setLinearVelocity(dir.scl(speed));

        // Si tu préfères un coup (impulse) plutôt qu'une vitesse figée :
        // float impulseMag = ballBody.getMass() * speed;
        // ballBody.applyLinearImpulse(dir.scl(impulseMag), ballBody.getWorldCenter(), true);
    }
}
