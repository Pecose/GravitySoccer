package entities.behavior.collision.bodys;

import entities.Entity;

public interface CollisionReactive {

	void onCollision(Entity self, Entity other);

}
