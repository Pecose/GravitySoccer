package entities.behavior.collision.ball;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.behavior.collision.bodys.CollisionReactive;
import players.Ball;
import players.Player;

public class BallTouchPlayerBehavior implements Behavior, CollisionReactive {

    @Override
    public void update(Control control, Entity entity) {
        // pas de logique par frame
    }

    @Override
    public void onCollision(Entity self, Entity other) {
        // on ne traite que balle vs joueur
        if (!(self instanceof Ball && other instanceof Player)) return;

        Body ballBody   = self.getBody();
        Body playerBody = other.getBody();
        if (ballBody == null || playerBody == null) return;

        // 1) vitesses dans le monde
        Vector2 vBall   = ballBody.getLinearVelocity();
        Vector2 vPlayer = playerBody.getLinearVelocity();

        // 2) calcul de la vitesse relative (balle par rapport au joueur)
        Vector2 vRel = vBall.cpy().sub(vPlayer);

        // 3) normale approximée = direction centre balle → centre joueur
        Vector2 n = ballBody.getWorldCenter()
                            .cpy()
                            .sub(playerBody.getWorldCenter())
                            .nor();

        // 4) réflexion de vRel sur n : vRel' = vRel - 2*(vRel·n)*n
        float dot = vRel.dot(n);
        Vector2 vRelReflected = vRel.cpy().sub(n.scl(2f * dot));

        // 5) recompose la vitesse dans le référentiel monde
        Vector2 vAfter = vRelReflected.add(vPlayer);

        // 6) applique directement sur la balle
        ballBody.setLinearVelocity(vAfter);
    }
}
