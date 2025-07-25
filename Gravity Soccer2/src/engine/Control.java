package engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import bumper.Bumper;
import edges.Edges;
import entities.Actor;
import entities.Entity;
import entities.Registry;
import entities.behavior.collision.bodys.CollisionManager;
import entities.cameras.FreeCamera;
import entities.cameras.FreeCameraObject;
import entities.video.VideoGoalCelebration;
import entities.world.PhysicsWorld;
import players.Ball;
import players.country.Bluegladesh;
import players.country.Redjistan;
import players.country.Team;
import players.side.leftTeam.LeftTeam;
import players.side.rightTeam.RightTeam;
import score.GameHUD;
import score.GoalManager;
import sound.SoundManager;
import world.MapRenderer;
import world.MapRendererObject;

public class Control extends ApplicationAdapter {
    public ShapeRenderer renderer;
    public SpriteBatch batch;
    public static FreeCamera camera;
    public static Team leftTeam = new Bluegladesh(new LeftTeam());
    public static Team rightTeam= new Redjistan(new RightTeam());
    public static SoundManager soundManager = new SoundManager("src/music/sf2/Super_Mario.sf2");
    public static final VideoGoalCelebration goalVideo = new VideoGoalCelebration();
    public static Sound crowd;
    
    @SuppressWarnings("unused")
	private DebugSystem debugSys;
    private MapRenderer mapRenderer;
    
    @Override
    public void create() {
    	renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        
        camera = new FreeCameraObject();
        FreeCamera.getCamera().position.set(0, 0, 0);
        mapRenderer = new MapRendererObject();

        PhysicsWorld.getWorld().setContactListener(new CollisionManager());
        debugSys = new DebugSystem(PhysicsWorld.getWorld(), FreeCamera.camera, PhysicsWorld.PPM);

        Registry.add(new Bumper(-960, -540, -960, -520, -940, -540), "Bumper3");
        Registry.add(new Bumper(960, -540, 960, -520, 940, -540), "Bumper4");
        Registry.add(new Bumper(-960, 540, -960, 520, -940, 540), "Bumper5");
        Registry.add(new Bumper(960, 540, 960, 520, 940, 540), "Bumper6");
        
        Registry.add(new Edges(-960, -540, 960*2, 1), "DownEdge");
        Registry.add(new Edges(-960, -540, 1, 540*2), "LeftEdge");
        Registry.add(new Edges(-960, 539, 960*2, 1), "BottomEdge");
        Registry.add(new Edges(959, -540, 1, 540*2), "RightEdge");
        
        Registry.add(new GameHUD(), "GameHUD");
        Registry.add(new Ball( 0,   0, 15), "Ball");
        
        Control.leftTeam.resetPlayers();
        Control.rightTeam.resetPlayers();

        soundManager.loadMidi("src/music/midi/Guile.mid");

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
        
        this.renderer.begin(ShapeRenderer.ShapeType.Filled);
        Registry.getMap().forEach((key, character) -> {
        	((Entity)character).render(this);
        });
        this.renderer.end();
    
        this.batch.begin();
        Registry.getMap().forEach((key, character) -> {
        	((Entity)character).batch(this);
        });
        
        this.batch.end();
        
        if (goalVideo != null && goalVideo.isPlaying()) {
            goalVideo.updateAndRender(batch);
        }
        
        Gdx.gl.glDisable(GL20.GL_BLEND);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        	GoalManager.onGoalScored(leftTeam.getSideTeam());
        }
        
    }


    @Override
    public void dispose() {
    	soundManager.dispose();
    	renderer.dispose();
        batch.dispose();
        goalVideo.dispose();
        Gdx.app.exit();
    }
}
