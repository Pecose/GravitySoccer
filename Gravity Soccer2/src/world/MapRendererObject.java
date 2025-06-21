package world;           // même package que l’interface MapRenderer

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import engine.Control;
import entities.cameras.FreeCamera;

public class MapRendererObject implements MapRenderer {

    /** Texture du terrain. */
    private final Texture mapTex;
    /** Sprite pour placer / redimensionner facilement. */
    private final Sprite  mapSprite;

    public MapRendererObject() {
        // charge la texture (PNG, JPG, etc.) depuis assets/
        mapTex = new Texture(Gdx.files.internal("map.png"));   // ou "images/map.png"
        mapTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // crée un sprite pour simplifier l'affichage
        mapSprite = new Sprite(mapTex);

        // option : centrer le terrain sur (0,0).
        // Si tu préfères partir de l’origine en bas-gauche, supprime ces deux lignes.
        mapSprite.setOriginCenter();
        mapSprite.setPosition(-mapSprite.getWidth()  / 2f,
                              -mapSprite.getHeight() / 2f);
    }

    /** Ici, rien de vraiment dynamique ; on pourrait animer le terrain si besoin. */
    @Override
    public void update(FreeCamera freeCamera, Control control) {
        // ex. : parallax, scrolling, changement de teinte…
    }

    /** Dessine la map avec le SpriteBatch commun au jeu. */
    @Override
    public void render(Control control) {
        SpriteBatch batch = control.batch;  

        batch.begin();
        mapSprite.draw(batch);
        batch.end();
    }

    /** Libère la mémoire GPU. */
    @Override
    public void dispose() {
        mapTex.dispose();
    }
}
