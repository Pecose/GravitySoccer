package entities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import entities.world.PhysicsWorld;

public class Registry {
    private static final Map<String, Entity> registry = new HashMap<>();

    public static void add(Entity obj, String key) {
    	registry.put(key, obj);
    }

    public static Entity get(String key) {
        return registry.get(key);
    }

    public static void set(Entity obj, String key) {
    	registry.replace(key, obj);
    }
    
    public static Collection<Entity> getAll() {
        return registry.values();
    }
    
    public static Map<String, Entity> getMap() {
        return registry;
    }

    public static void remove(String key) {
        Entity old = registry.remove(key);
        if (old != null) {
            if (old.getBody() != null) {
                PhysicsWorld.getWorld().destroyBody(old.getBody());
            }
        }
    }
}
