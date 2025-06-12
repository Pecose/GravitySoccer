package entities.behavior.collision;

import entities.Entity;

public interface CollisionReactive {

	void onCollision(Entity self, Entity other);

}
