package entities.behavior.flame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import engine.Control;
import entities.Entity;
import entities.Registry;

public class FlameTrailSegment extends Entity {
 private final String key;
 private final float maxLength, width, rotation, lifeTime;
 private float age = 0f;

 public FlameTrailSegment(String key, float x, float y,
                          float maxLength, float width,
                          float rotation, float lifeTime) {
     super(x, y); // positionne l'entité, crée un Body (inutile ici)
     this.key       = key;
     this.maxLength = maxLength;
     this.width     = width;
     this.rotation  = rotation;
     this.lifeTime  = lifeTime;
 }

 @Override
 public void batch(Control control) {
     age += Gdx.graphics.getDeltaTime();
     if (age >= lifeTime) {
         Registry.remove(key);
     }
 }

 @Override
 public void render(Control control) {
     if (age >= lifeTime) return;

     float t       = age / lifeTime;
     float length  = maxLength * (1f - t);
     float green   = 1f - t;
     float alpha   = 1f - t;
     Color col     = new Color(1f, green, 0f, alpha);

     // Prépare le ShapeRenderer
     ShapeRenderer r = control.renderer;
     r.setColor(col);

     // Centre de l'entité
     float cx = getPosX();
     float cy = getPosY();
     float hl = length / 2f;
     float hw = width  / 2f;

     // Coins locaux du rectangle
     Vector2[] pts = {
         new Vector2(-hl, -hw),
         new Vector2( hl, -hw),
         new Vector2( hl,  hw),
         new Vector2(-hl,  hw),
     };

     // Rotation + translation
     float cos = (float)Math.cos(rotation), sin = (float)Math.sin(rotation);
     for (Vector2 p : pts) {
         float x = p.x * cos - p.y * sin + cx;
         float y = p.x * sin + p.y * cos + cy;
         p.set(x, y);
     }

     // Dessine deux triangles pour remplir le quad
     r.triangle(
         pts[0].x, pts[0].y,
         pts[1].x, pts[1].y,
         pts[2].x, pts[2].y
     );
     r.triangle(
         pts[2].x, pts[2].y,
         pts[3].x, pts[3].y,
         pts[0].x, pts[0].y
     );
 }
}
