package engine;

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
import entities.Registry;
import entities.behavior.collision.bodys.CollisionManager;
import entities.cameras.FreeCamera;
import entities.world.PhysicsWorld;
import goal.BlueGoal;
import goal.RedGoal;
import players.Ball;
import players.blueTeam.BlueDef;
import players.redTeam.RedDef;
import players.team.Side;
import score.GameHUD;
import world.MapRenderer;

public class Control extends ApplicationAdapter {
    public ShapeRenderer renderer;
    public SpriteBatch batch;
    public static FreeCamera camera;

    private MapRenderer mapRenderer;
    
    @Override
    public void create() {
    	renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        
        camera = OG.get().newFreeCamera();
        FreeCamera.getCamera().position.set(0, 0, 0);
        mapRenderer = OG.get().newMapRenderer();

        PhysicsWorld.get().setContactListener(new CollisionManager());
        
        Registry.add(new Bumper(-10, -540, 10, -540, 0, -530), "Bumper1");
        Registry.add(new Bumper(-10, 540, 10, 540, 0, 530), "Bumper2");
        
        Registry.add(new Bumper(-960, -540, -960, -520, -940, -540), "Bumper3");
        Registry.add(new Bumper(960, -540, 960, -520, 940, -540), "Bumper4");
        Registry.add(new Bumper(-960, 540, -960, 520, -940, 540), "Bumper5");
        Registry.add(new Bumper(960, 540, 960, 520, 940, 540), "Bumper6");
        
        Registry.add(new DownEdge(-960, -540, 960*2, 1), "DownEdge");
        Registry.add(new Edges(-960, -540, 1, 540*2), "LeftEdge");
        Registry.add(new BottomEdge(-960, 539, 960*2, 1), "BottomEdge");
        Registry.add(new Edges(959, -540, 1, 540*2), "RightEdge");
        
        int x, y;
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new BlueDef(-840 + x,   0 + y,   15, Side.LEFT), "Blue1");
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new BlueDef(-560 + x, -270 + y, 15, Side.LEFT), "Blue2");
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new BlueDef(-560 + x,   0 + y,   15, Side.LEFT), "Blue3");
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new BlueDef(-560 + x,  270 + y, 15, Side.LEFT), "Blue4");
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new BlueDef(-200 + x, -360 + y, 15, Side.LEFT), "Blue5");
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new BlueDef(-200 + x,   0 + y,   15, Side.LEFT), "Blue6");
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new BlueDef(-200 + x,  360 + y, 15, Side.LEFT), "Blue7");
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new BlueDef(-180 + x, -180 + y, 15, Side.LEFT), "Blue8");
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new BlueDef(-180 + x,  180 + y, 15, Side.LEFT), "Blue9");
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new BlueDef(-700 + x,   0 + y,  15, Side.LEFT), "Blue10");
        
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new RedDef( 840 + x,   0 + y,   15, Side.RIGHT), "Red1");
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new RedDef( 560 + x, -270 + y, 15, Side.RIGHT), "Red2");
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new RedDef( 560 + x,   0 + y,   15, Side.RIGHT), "Red3");
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new RedDef( 560 + x,  270 + y, 15, Side.RIGHT), "Red4");
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new RedDef( 200 + x, -360 + y, 15, Side.RIGHT), "Red5");
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new RedDef( 200 + x,   0 + y,   15, Side.RIGHT), "Red6");
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new RedDef( 200 + x,  360 + y, 15, Side.RIGHT), "Red7");
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new RedDef( 180 + x, -180 + y, 15, Side.RIGHT), "Red8");
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new RedDef( 180 + x,  180 + y, 15, Side.RIGHT), "Red9");
        x = (int) ((Math.random() * 20) - 10);
        y = (int) ((Math.random() * 20) - 10);
        Registry.add(new RedDef( 700 + x,   0 + y,  15, Side.RIGHT), "Red10"); 
         
        Registry.add(new RedGoal(), "RightGoal");
        Registry.add(new BlueGoal(), "LeftGoal");
        Registry.add(new Ball( 0,   0, 15), "Ball");
        Registry.add(new GameHUD(), "GameHUD");
       

    }

    @SuppressWarnings("unused")
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
        
        Registry.getMap().forEach((key, character) -> {
        	((Entity)character).render(this);
        });
        
    }


    @Override
    public void dispose() {
    	renderer.dispose();
        batch.dispose();
        Gdx.app.exit();
    }
}
