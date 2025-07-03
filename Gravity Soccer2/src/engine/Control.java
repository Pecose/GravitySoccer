package engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import bumper.Bumper;
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
import players.country.Bluegladesh;
import players.country.Redjistan;
import players.country.Team;
import players.side.leftTeam.LeftTeam;
import players.side.rightTeam.RightTeam;
import score.GameHUD;
import sound.SoundManager;
import world.MapRenderer;

public class Control extends ApplicationAdapter {
    public ShapeRenderer renderer;
    public SpriteBatch batch;
    public static FreeCamera camera;
    public static Team leftTeam = new Redjistan(new LeftTeam());
    public static Team rightTeam= new Bluegladesh(new RightTeam());
    public static SoundManager soundManager = new SoundManager("src/music/sf2/Super_Mario.sf2");
    @SuppressWarnings("unused")
	private DebugSystem debugSys;

    private MapRenderer mapRenderer;
    
    

    
    @Override
    public void create() {
    	renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        
        camera = OG.get().newFreeCamera();
        FreeCamera.getCamera().position.set(0, 0, 0);
        mapRenderer = OG.get().newMapRenderer();

        PhysicsWorld.getWorld().setContactListener(new CollisionManager());
        debugSys = new DebugSystem(PhysicsWorld.getWorld(), FreeCamera.camera, PhysicsWorld.PPM);

//        Registry.add(new Bumper(-10, -540, 10, -540, 0, -530), "Bumper1");
//        Registry.add(new Bumper(-10, 540, 10, 540, 0, 530), "Bumper2");
        
        Registry.add(new Bumper(-960, -540, -960, -520, -940, -540), "Bumper3");
        Registry.add(new Bumper(960, -540, 960, -520, 940, -540), "Bumper4");
        Registry.add(new Bumper(-960, 540, -960, 520, -940, 540), "Bumper5");
        Registry.add(new Bumper(960, 540, 960, 520, 940, 540), "Bumper6");
        
        Registry.add(new Edges(-960, -540, 960*2, 1), "DownEdge");
        Registry.add(new Edges(-960, -540, 1, 540*2), "LeftEdge");
        Registry.add(new Edges(-960, 539, 960*2, 1), "BottomEdge");
        Registry.add(new Edges(959, -540, 1, 540*2), "RightEdge");
        
        Registry.add(new RedGoal(), "RightGoal");
        Registry.add(new BlueGoal(), "LeftGoal");
        Registry.add(new GameHUD(), "GameHUD");
        Registry.add(new Ball( 0,   0, 15), "Ball");
        
        Control.leftTeam.resetPlayers();
        Control.rightTeam.resetPlayers();

        soundManager.loadMidi("src/music/midi/Pirates of the Caribbean.mid");
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
        PhysicsWorld.getWorld().step(dt, 6, 2);
//        debugSys.render(); // debug !!!!!!
        Registry.getMap().forEach((key, character) -> {
        	this.renderer.begin(ShapeRenderer.ShapeType.Filled);
        	((Entity)character).render(this);
        	this.renderer.end();
        });
        
        Registry.getMap().forEach((key, character) -> {
        	this.batch.begin();
        	((Entity)character).batch(this);
        	this.batch.end();
        });
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        	soundManager.playNextNote();
        }
    }


    @Override
    public void dispose() {
    	soundManager.dispose();
    	renderer.dispose();
        batch.dispose();
        Gdx.app.exit();
    }
}
