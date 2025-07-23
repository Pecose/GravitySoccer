package entities;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class FixtureManager {
    private final Body body;
    private final Map<String, Fixture> fixtures = new HashMap<>();

    public FixtureManager(Body body) {
        this.body = body;
    }

    public void setFixture(String key, FixtureDef def) {
        if (fixtures.containsKey(key)) {
            body.destroyFixture(fixtures.get(key));
        }
        fixtures.put(key, body.createFixture(def));
        def.shape.dispose();
    }

    public void removeFixture(String key) {
        Fixture f = fixtures.remove(key);
        if (f != null) body.destroyFixture(f);
    }

    public Map<String, Fixture> getFixtures() {
        return Collections.unmodifiableMap(fixtures);
    }
    
    public Fixture getFixture(String key) {
    	return this.fixtures.get(key);
    }
    
    public boolean hasFixture(String key) {
        return fixtures.containsKey(key);
    }
    
    public void clearAll() {
        for (Fixture f : fixtures.values()) {
            body.destroyFixture(f);
        }
        fixtures.clear();
    }
}
