package entities.behavior.flame;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

import engine.Control;
import entities.Entity;
import entities.Registry;
import entities.behavior.Behavior;
import entities.world.PhysicsWorld;

/**
 * Trace un sillage de flammes sans espaces, même à grande vitesse,
 * en interpolant selon la distance parcourue et un seuil de vitesse.
 */
public class FlameTrailBehavior implements Behavior {
    private static final float SEG_LEN     = 20f;   // longueur du segment
    private static final float SEG_WID     = 6f;    // épaisseur (et espacement cible)
    private static final float SEG_LIFE    = 0.5f;  // durée de vie en secondes
    private static final float MIN_SPEED   = 500f;   // vitesse minimale pour émettre

    // dernière position connue pour interpolation
    private Vector2 lastPos = null;
    private int     counter = 0;
    private Random  rand    = new Random();

    @Override
    public void render(Control control, Entity entity) {
        // position actuelle
        Vector2 currentPos = new Vector2(entity.getPosX(), entity.getPosY());
        if (lastPos == null) {
            lastPos = currentPos.cpy();
            return;
        }

        // calcul de la vitesse en px/s
        Vector2 vel = entity.getBody().getLinearVelocity();
        float speed = vel.len() * PhysicsWorld.PPM;
        if (speed < MIN_SPEED) {
            // si trop lent, on ne trace rien et on remet à jour la position de référence
            lastPos = currentPos.cpy();
            return;
        }

        // vecteur déplacement depuis la dernière frame
        Vector2 movement = currentPos.cpy().sub(lastPos);
        float dist = movement.len();
        if (dist < SEG_WID) {
            // pas assez bougé pour générer un segment complet
            return;
        }

        // direction normalisée du déplacement
        Vector2 dir = movement.cpy().nor();
        // nombre de segments nécessaires pour combler les blancs
        int count = (int) Math.floor(dist / SEG_WID);

        // générer chaque segment le long du vecteur de déplacement
        for (int i = 1; i <= count; i++) {
            Vector2 pos = lastPos.cpy().add(dir.cpy().scl(i * SEG_WID));
            emitSegmentAt(entity, pos, dir);
        }

        // mise à jour de la dernière position de référence
        lastPos.add(dir.scl(count * SEG_WID));
    }

    /**
     * Crée un segment de flamme à une position précise et avec l'orientation.
     */
    private void emitSegmentAt(Entity entity, Vector2 pos, Vector2 dir) {
        // angle et rotation perpendiculaire aléatoire
        float ang = (float) Math.atan2(dir.y, dir.x);
        float rot = (float) (ang + ((rand.nextBoolean() ? 1 : -1) * (Math.PI / 2f)));

        // décalage aléatoire perpendiculaire pour l'effet organique
        Vector2 perp = new Vector2((float) Math.cos(rot), (float) Math.sin(rot))
                             .scl((rand.nextFloat() - 0.5f) * SEG_WID * 1.5f);
        Vector2 spawn = pos.cpy().add(perp);

        // création et enregistrement du segment
        String key = "FlameSeg_" + System.currentTimeMillis() + "_" + (counter++);
        FlameTrailSegment seg = new FlameTrailSegment(
            key,
            spawn.x, spawn.y,
            SEG_LEN, SEG_WID,
            rot,
            SEG_LIFE
        );
        Registry.add(seg, key);
    }

	@Override
	public void batch(Control control, Entity entity) {
		// TODO Auto-generated method stub
		
	}
}
