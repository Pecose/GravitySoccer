package entities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import entities.world.PhysicsWorld;

public class Registry {
	private static final Map<String, Entity> registry = new HashMap<>();
	private static final Map<String, Entity> addBuffer = new HashMap<>();
	private static final Map<String, Entity> subBuffer = new HashMap<>();

    public static void add(Entity obj, String key) {
    	addBuffer.put(key, obj);
    }

    private static void addFlush() {
    	registry.putAll(addBuffer);
    	addBuffer.clear();
    }
    
    private static void subFlush() {
    	for(String key : subBuffer.keySet()) {
    		Entity old = registry.remove(key);
            if (old != null) {
                if (old.getBody() != null) {
                    PhysicsWorld.getWorld().destroyBody(old.getBody());
                }
            }
    	}
    	subBuffer.clear();
    }
    
    public static Entity get(String key) {
        return registry.get(key);
    }

    public static void set(Entity obj, String key) {
    	subFlush();
    	addFlush();
    	registry.replace(key, obj);
    }
    
    public static Collection<Entity> getAll() {
    	subFlush();
    	addFlush();
        return registry.values();
    }
    
    public static Map<String, Entity> getMap() {
    	subFlush();
    	addFlush();
        return registry;
    }

    public static void remove(String key) {
        subBuffer.put(key, registry.get(key));
    }
}
