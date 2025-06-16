package entities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import entities.behavior.Behavior;
import entities.behavior.collision.bodys.CollisionReactive;
import entities.world.PhysicsWorld;

public abstract class Entity implements Actor{

	private Body body;
	private float size;
	private float width;
	private float height;
	
	private int x1;
	private int y1; 
	private int x2; 
	private int y2; 
	private int x3; 
	private int y3;

	 private Map<Class<? extends Behavior>, Behavior> behaviors = new HashMap<>();
    
    public Entity() {
    	this(0, 0);
    }
    
    public Entity(float x, float y) {
    	this(x, y, 1);
    }
    
    public Entity(float x, float y, float s) {
        this.size = s;
        World world = PhysicsWorld.get();
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(x / PhysicsWorld.PPM, y / PhysicsWorld.PPM);
        body = world.createBody(bd);
        body.setUserData(this);

    }
    
    public Entity(float x, float y, float w, float h) {
    	this.width = w;
    	this.height = h;
        World world = PhysicsWorld.get();
        BodyDef bd = new BodyDef();
//        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(x / PhysicsWorld.PPM, y / PhysicsWorld.PPM);
        body = world.createBody(bd);
        body.setUserData(this);
      
    }
    
    public Entity(int x1, int y1, int x2, int y2, int x3, int y3) {
        this.setX1(x1);
        this.setY1(y1);
        this.setX2(x2);
        this.setY2(y2);
        this.setX3(x3);
        this.setY3(y3);

        // 🔹 Création du Body statique au centre du triangle
        Vector2 p1 = new Vector2(x1, y1).scl(1f / PhysicsWorld.PPM);
        Vector2 p2 = new Vector2(x2, y2).scl(1f / PhysicsWorld.PPM);
        Vector2 p3 = new Vector2(x3, y3).scl(1f / PhysicsWorld.PPM);

        // 🔹 Calcul du centre
        Vector2 center = new Vector2(
            (p1.x + p2.x + p3.x) / 3f,
            (p1.y + p2.y + p3.y) / 3f
        );

        // 🔹 Création du Body statique
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set(center);

        Body body = PhysicsWorld.get().createBody(bd);
        body.setUserData(this);

        this.setBody(body);
    }


	public void onCollision(Entity other) {
        for (Behavior b : getBehaviors()) {
            if (b instanceof CollisionReactive) {
                ((CollisionReactive) b).onCollision(this, other);
            }
        }
    }

    
    @Override
    public float getPosX() {
        return body.getPosition().x * PhysicsWorld.PPM;
    }

    /** Retourne la position Y en pixels, d’après le Body en mètres. */
    @Override
    public float getPosY() {
        return body.getPosition().y * PhysicsWorld.PPM;
    }

    /** Ne sert plus : on ne stocke plus de position séparée. */
    @Override
    public Actor setPosX(float x) {
        // inutile ou tu peux teleporter directement via body.setTransform(x/PPM,…)
        return this;
    }
    @Override
    public Actor setPosY(float y) {
        return this;
    }
    
    public float getSize() {
    	return this.size;
    }
    
    public void setSize(float size) {
    	this.size = size;
    }
    
    public float getWidth() {
    	return this.width;
    }
    
    public void setWidth(float width) {
    	this.width = width;
    }
    
    public float getHeight() {
    	return this.height;
    }
    
    public void setHeight(float height) {
    	this.height = height;
    }

    @Override
    public Body getBody() {
    	return this.body;
    }
    
    @Override
    public void setBody(Body body) {
    	this.body = body;
    }
    
	@Override
	public <T extends Behavior> void addBehavior(Class<T> categoryType, T behavior) {
        behaviors.put(categoryType, behavior);
    }
	
	@Override
    public Collection<Behavior>  getBehaviors() {
        return behaviors.values();
    }
	
	@Override
    public <T extends Behavior> void removeBehavior(Class<T> categoryType) {
        behaviors.remove(categoryType);
    }

	public void setPosition(Vector2 position) {
		this.getBody().getPosition().set(position);
	}

	public void setVelocity(Vector2 linearVelocity) {
		this.getBody().getLinearVelocity().set(linearVelocity);
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public int getX3() {
		return x3;
	}

	public void setX3(int x3) {
		this.x3 = x3;
	}

	public int getY3() {
		return y3;
	}

	public void setY3(int y3) {
		this.y3 = y3;
	}
;

}
