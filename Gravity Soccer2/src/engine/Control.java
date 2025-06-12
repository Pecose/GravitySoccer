package engine;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import bumper.Bumper;
import edges.BottomEdge;
import edges.DownEdge;
import edges.Edges;
import engine.manager.OG;
import entities.Actor;
import entities.Entity;
import entities.behavior.collision.CollisionManager;
import entities.cameras.FreeCamera;
import entities.world.PhysicsWorld;
import players.Ball;
import players.blueTeam.BlueDef;
import players.redTeam.RedDef;
import score.GameHUD;
import world.MapRenderer;

public class Control extends ApplicationAdapter {
    public ShapeRenderer renderer;
    public SpriteBatch batch;
    public static FreeCamera camera;

    private List<Entity> entities;
    private MapRenderer mapRenderer;
    
    @Override
    public void create() {
    	renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        entities = new ArrayList<>();
        
        camera = OG.get().newFreeCamera();
        FreeCamera.getCamera().position.set(0, 0, 0);
        mapRenderer = OG.get().newMapRenderer();

        PhysicsWorld.get().setContactListener(new CollisionManager());
        
        
        entities.add(new Bumper(-10, -540, 10, -540, 0, -530));
        entities.add(new Bumper(-10, 540, 10, 540, 0, 530));
        
        entities.add(new Bumper(-960, -540, -960, -520, -940, -540));
        entities.add(new Bumper(960, -540, 960, -520, 940, -540));
        entities.add(new Bumper(-960, 540, -960, 520, -940, 540));
        entities.add(new Bumper(960, 540, 960, 520, 940, 540));
        
        entities.add(new DownEdge(-960, -540, 960*2, 1));
        entities.add(new Edges(-960, -540, 1, 540*2));
        entities.add(new BottomEdge(-960, 539, 960*2, 1));
        entities.add(new Edges(959, -540, 1, 540*2));
        
        int x, y;
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new BlueDef(-840 + x,   0 + y,   15));
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new BlueDef(-560 + x, -270 + y, 15));
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new BlueDef(-560 + x,   0 + y,   15));
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new BlueDef(-560 + x,  270 + y, 15));
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new BlueDef(-200 + x, -360 + y, 15));
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new BlueDef(-200 + x,   0 + y,   15));
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new BlueDef(-200 + x,  360 + y, 15));
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new BlueDef(-180 + x, -180 + y, 15));
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new BlueDef(-180 + x,  180 + y, 15));
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new BlueDef(-700 + x,   0 + y,  15));
        
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new RedDef( 840 + x,   0 + y,   15));
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new RedDef( 560 + x, -270 + y, 15));
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new RedDef( 560 + x,   0 + y,   15));
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new RedDef( 560 + x,  270 + y, 15));
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new RedDef( 200 + x, -360 + y, 15));
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new RedDef( 200 + x,   0 + y,   15));
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new RedDef( 200 + x,  360 + y, 15));
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new RedDef( 180 + x, -180 + y, 15));
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new RedDef( 180 + x,  180 + y, 15));
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        entities.add(new RedDef( 700 + x,   0 + y,  15)); 
        
        entities.add(new Ball( 0,   0, 15));
        entities.add(new GameHUD());
    }

    @Override
    public void render() {
        ((Actor) camera).render(this);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setProjectionMatrix(FreeCamera.getCamera().combined);
        batch.setProjectionMatrix(FreeCamera.getCamera().combined);
        
        mapRenderer.update(camera, this);
        mapRenderer.render(this);
        
        float dt = Gdx.graphics.getDeltaTime();
        PhysicsWorld.get().step(dt, 6, 2);
        
        for (Entity character : entities) {
        	character.render(this);
        }
        
    }


    @Override
    public void dispose() {
    	renderer.dispose();
        batch.dispose();
        Gdx.app.exit();
    }
}
