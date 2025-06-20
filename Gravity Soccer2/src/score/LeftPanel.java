package score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import engine.Control;

public class LeftPanel {
    private BitmapFont font;
    private SpriteBatch batch;
    private String teamLeftAbbr = "BLU";
    private Texture flagLeft = new Texture("flags/FRA.png");

    private static final float BOX_X          = 20;
    private static final float BOX_Y          = Gdx.graphics.getHeight() - 120;
    private static final float BOX_WIDTH      = 120;
    private static final float BOX_HEIGHT     = 100;

    // Hauteur réservée au timer en haut du HUD
    private static final float TIMER_HEIGHT    = 25f;
    // Sous-blocs verticaux
    private static final float ABBR_BOX_HEIGHT = 25f;
    private static final float FLAG_BOX_HEIGHT = 8f;
    private static final float SCORE_BOX_WIDTH = 37f;

    public LeftPanel(SpriteBatch batch) {
        this.font = new BitmapFont();
        font.getData().setScale(1.2f);
        this.batch = batch;
    }

    public void render(Control control) {
        GlyphLayout layout = new GlyphLayout();
        // Colonne de gauche décallée de 10 px
        float colX = BOX_X + 10;

        // Y du bas du contenu (juste sous le timer)
        float contentTopY = BOX_Y + BOX_HEIGHT - TIMER_HEIGHT;

        // --- 1) Abréviation ---
        float abbrBlockY = contentTopY - ABBR_BOX_HEIGHT;
        font.setColor(Color.BLACK);
        layout.setText(font, teamLeftAbbr);
        float abbrTextX = colX;
        float abbrTextY = abbrBlockY + (ABBR_BOX_HEIGHT + layout.height) / 2f;
        // Faux‐gras
        font.draw(batch, layout, abbrTextX, abbrTextY);
        font.draw(batch, layout, abbrTextX + 1, abbrTextY);

        // --- 2) Drapeau ---
        float flagBlockY = abbrBlockY - FLAG_BOX_HEIGHT;
        float flagDrawW  = 40f;
        float flagDrawH  = 8f;
        float flagX      = colX;
        float flagY      = flagBlockY + (FLAG_BOX_HEIGHT - flagDrawH) / 2f;
        batch.draw(flagLeft, flagX, flagY, flagDrawW, flagDrawH);

        // --- 3) Score ---
        float scoreBlockY      = BOX_Y;
        float usedHeight       = TIMER_HEIGHT + ABBR_BOX_HEIGHT + FLAG_BOX_HEIGHT;
        float scoreBlockHeight = BOX_HEIGHT - usedHeight;

        font.setColor(Color.WHITE);
        font.getData().setScale(3f);
        layout.setText(font, String.valueOf(GameScore.getLeftScore()));

        float scoreTextX = colX + (SCORE_BOX_WIDTH - layout.width) / 2f;
        float scoreTextY = scoreBlockY + (scoreBlockHeight + layout.height) / 2f;
        font.draw(batch, layout, scoreTextX, scoreTextY);
    }
}
