package entities.behavior.collision;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import entities.Entity;

public class CollisionManager implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Entity eA = getEntityFromFixture(contact.getFixtureA());
        Entity eB = getEntityFromFixture(contact.getFixtureB());

        if (eA != null && eB != null) {
            eA.onCollision(eB);
            eB.onCollision(eA);
        }
    }

    @Override
    public void endContact(Contact contact) {
        // Optionnel : quand la collision se termine
    }

    // Pas utile pour toi pour le moment :
    @Override public void preSolve(Contact contact, Manifold oldManifold) {}
    @Override public void postSolve(Contact contact, ContactImpulse impulse) {}

    private Entity getEntityFromFixture(Fixture fixture) {
        Body body = fixture.getBody();
        Object data = body.getUserData();
        if (data instanceof Entity) {
            return (Entity) data;
        }
        return null;
    }
    
}
