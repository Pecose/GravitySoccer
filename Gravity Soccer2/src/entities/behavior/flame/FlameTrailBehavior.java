package entities.behavior.flame;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import engine.Control;
import entities.Entity;
import entities.Registry;
import entities.behavior.Behavior;
import entities.world.PhysicsWorld;

public class FlameTrailBehavior implements Behavior {
 private static final float SEG_LEN    = 20f;    // longueur initiale
 private static final float SEG_WID    = 6f;     // épaisseur
 private static final float SEG_LIFE   = 0.5f;   // en secondes
 private static final float MIN_SPEED  = 500f;   // px/s pour émettre
 private static final float INTERVALE  = 0.005f;  // sec entre deux emits

 private float accumulator = 0f;
 private int   counter     = 0;
 private Random rand       = new Random();

 @Override
 public void update(Control control, Entity entity) {
     float delta = Gdx.graphics.getDeltaTime();
     accumulator += delta;

     // vitesse en px/s
     float speed = entity.getBody().getLinearVelocity().len() * PhysicsWorld.PPM;
     if (speed < MIN_SPEED || accumulator < INTERVALE) return;

     accumulator = 0f;

     // calcul de l'angle de la vélocité
     Vector2 vel = entity.getBody().getLinearVelocity();
     float ang   = (float)Math.atan2(vel.y, vel.x);

     // rotation du segment perpendiculaire à la trajectoire
     float rot   = ang + ((rand.nextBoolean() ? 1 : -1) * ((float)Math.PI / 2f));

     // position du segment (juste derrière la balle)
     float bx = entity.getPosX(), by = entity.getPosY();
     Vector2 back = vel.cpy().nor().scl(-entity.getSize()*0.6f);
     Vector2 perp = new Vector2((float)Math.cos(rot), (float)Math.sin(rot))
                       .scl((rand.nextFloat()-0.5f)*SEG_WID*1.5f);

     Vector2 pos = new Vector2(bx, by).add(back).add(perp);

     // crée une clé unique pour le Registry
     String key = "FlameSeg_" + System.currentTimeMillis() + "_" + (counter++);

     // instancie et enregistre le segment
     FlameTrailSegment seg = new FlameTrailSegment(
         key,
         pos.x, pos.y,
         SEG_LEN, SEG_WID,
         rot, SEG_LIFE
     );
     Registry.add(seg, key);
 }
}
