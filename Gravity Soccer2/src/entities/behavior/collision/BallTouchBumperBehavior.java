package entities.behavior.collision;

import com.badlogic.gdx.math.Vector2;
import bumper.Bumper;
import entities.Entity;
import entities.behavior.Behavior;

public class BallTouchBumperBehavior implements Behavior, CollisionReactive {

    @Override
    public void update(engine.Control control, Entity entity) {
        // rien à faire à chaque frame
    }

    @Override
    public void onCollision(Entity self, Entity other) {
        if (!(other instanceof Bumper)) return;

        Vector2 ballPos = self.getBody().getPosition();
        Vector2 bumperPos = other.getBody().getPosition();

        // Direction de rebond : de bumper vers balle
        Vector2 direction = ballPos.cpy().sub(bumperPos).nor();

        // Sécurité : éviter vecteur nul
        if (direction.isZero(0.001f)) {
            direction.set(0, 1); // rebond vertical par défaut si confondu
        }

        // Impulsion plus douce si besoin
        Vector2 impulse = direction.scl(1.2f); // tu peux adapter

        self.getBody().applyLinearImpulse(impulse, self.getBody().getWorldCenter(), true);
    }
}
