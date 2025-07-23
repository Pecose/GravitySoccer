package entities.behavior.collision.goalzone;

import java.util.concurrent.atomic.AtomicReference;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import entities.behavior.Behavior;
import entities.behavior.collision.bodys.CollisionReactive;

public interface GoalZoneBehavior extends Behavior, CollisionReactive{
	final AtomicReference<FixtureDef> fixtureDef = new AtomicReference<>(new FixtureDef());
}
