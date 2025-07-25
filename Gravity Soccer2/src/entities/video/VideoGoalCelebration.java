package entities.video;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;

public class VideoGoalCelebration implements Disposable {

    private VideoPlayer player;
    private boolean playing;
    private Runnable onComplete;

    private float x = 0, y = 0, w = -1, h = -1;
    private boolean keepAspect = true;
    private boolean allowSkip = true;
//    private float alpha = 1f;

    private ShaderProgram chromaShader;   // lazy
    private boolean shaderReady = false;

    // --- Vertex par défaut de SpriteBatch (copié depuis le code source LibGDX) ---
    private static final String DEFAULT_VERT =
        "attribute vec4 a_position;\n" +
        "attribute vec4 a_color;\n" +
        "attribute vec2 a_texCoord0;\n" +
        "uniform mat4 u_projTrans;\n" +
        "varying vec4 v_color;\n" +
        "varying vec2 v_texCoords;\n" +
        "void main(){\n" +
        "   v_color = a_color;\n" +
        "   v_texCoords = a_texCoord0;\n" +
        "   gl_Position = u_projTrans * a_position;\n" +
        "}";

    public VideoGoalCelebration() {
        // NE RIEN FAIRE qui touche à OpenGL ici
    }

    public void play(String path, Runnable onComplete) {
        stop();
        try {
            this.onComplete = onComplete;
            player = VideoPlayerCreator.createVideoPlayer();
            FileHandle file = Gdx.files.internal(path);
            player.load(file);
            player.play();
            playing = true;
        } catch (Exception e) {
            e.printStackTrace();
            if (onComplete != null) Gdx.app.postRunnable(onComplete);
        }
    }

    public void updateAndRender(SpriteBatch batch) {
        if (!playing || player == null) return;

        // Avance la vidéo (si dispo)
        try { player.update(); } catch (Throwable ignored) {}

        if (allowSkip && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            finish();
            return;
        }

        Texture frame = player.getTexture();
        if (frame == null) return;

        ensureBounds(frame);
        ensureShader(); // compile si besoin

        // Sauvegarde matrices & shader
        Matrix4 oldProj = batch.getProjectionMatrix().cpy();
        Matrix4 oldTrans = batch.getTransformMatrix().cpy();
        ShaderProgram oldShader = batch.getShader();

        // Coord écran
        batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        batch.setTransformMatrix(new Matrix4());

        // Shader chroma
        batch.setShader(chromaShader);
        chromaShader.bind();
        chromaShader.setUniformi("u_texture", 0);
        chromaShader.setUniformf("u_texelSize", 1f/frame.getWidth(), 1f/frame.getHeight());
        chromaShader.setUniformf("u_low",    0.05f);
        chromaShader.setUniformf("u_high",   0.15f);
        chromaShader.setUniformf("u_despill",0.5f);
        

        boolean began = !batch.isDrawing();
        if (began) batch.begin();
        float oldA = batch.getColor().a;
        batch.setColor(1f, 1f, 1f, oldA);
        batch.draw(frame, x, y, w, h);
        batch.setColor(1f, 1f, 1f, oldA);
        if (began) batch.end();

        // Restore
        batch.setProjectionMatrix(oldProj);
        batch.setTransformMatrix(oldTrans);
        batch.setShader(oldShader);

        if (!player.isPlaying()) finish();
    }

    public boolean isPlaying() { return playing; }

    public void setBounds(float x, float y, float w, float h) {
        this.x=x; this.y=y; this.w=w; this.h=h;
    }
    public void setFullScreen(){ this.w=-1; this.h=-1; }
    public void setKeepAspect(boolean keep){ this.keepAspect=keep; }
    public void setAllowSkip(boolean allow){ this.allowSkip=allow; }
//    public void setAlpha(float a){ this.alpha=a; }

    public void stop() {
        if (player != null) {
            try { player.stop(); } catch (Throwable ignored) {}
            try { player.dispose(); } catch (Throwable ignored) {}
            player = null;
        }
        playing = false;
        onComplete = null;
    }

    @Override public void dispose() {
        stop();
        if (chromaShader != null) chromaShader.dispose();
    }

    // ---------- Privé ----------

    private void ensureShader() {
        if (shaderReady) return;
        String frag = Gdx.files.internal("shaders/video.frag").readString();
        chromaShader = new ShaderProgram(DEFAULT_VERT, frag);
        if (!chromaShader.isCompiled())
            throw new IllegalStateException("Shader error:\n" + chromaShader.getLog());
        shaderReady = true;
    }

    private void ensureBounds(Texture frame) {
        if (w >= 0 && h >= 0) return;
        float sw = Gdx.graphics.getWidth();
        float sh = Gdx.graphics.getHeight();

        if (!keepAspect) { x=0; y=0; w=sw; h=sh; return; }

        int vw = player.getVideoWidth() > 0 ? player.getVideoWidth() : frame.getWidth();
        int vh = player.getVideoHeight() > 0 ? player.getVideoHeight() : frame.getHeight();

        float scale = Math.min(sw / vw, sh / vh);
        w = vw * scale;
        h = vh * scale;
        x = (sw - w) * 0.5f;
        y = (sh - h) * 0.5f;
    }

    private void finish() {
        playing = false;
        Runnable cb = onComplete;
        stop();
        if (cb != null) Gdx.app.postRunnable(cb);
    }
}
