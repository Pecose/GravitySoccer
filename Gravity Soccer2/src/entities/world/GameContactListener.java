package entities.world;

import com.badlogic.gdx.physics.box2d.*;

public class GameContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        Object a = bodyA.getUserData();   // ce que TU as stocké dedans
        Object b = bodyB.getUserData();

        // Exemples d’actions :
//        if (a instanceof Goal && b instanceof Ball) {
//            ((Goal) a).score((Ball) b);
//        } else if (b instanceof Goal && a instanceof Ball) {
//            ((Goal) b).score((Ball) a);
//        }
        // tu peux aussi jouer un son, flasher la bille, etc.
    }

    @Override public void endContact(Contact c) { }       // optionnel
    @Override public void preSolve(Contact c, Manifold m) { }
    @Override public void postSolve(Contact c, ContactImpulse i) { }
}
