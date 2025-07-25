#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_uvWorld;
varying vec2 v_worldPos;

uniform sampler2D u_texture;
uniform vec2 u_ballCenter;   // centre de la balle en world-coords
uniform float u_radius;      // rayon de la balle en world-coords
uniform vec2 u_tileSize;     // taille (w,h) de ta texture
uniform bool u_circleMask;   // active/désactive le clipping circulaire

// seuils pour le chroma-key
const float GREEN_THRESHOLD = 0.8;
const float NON_GREEN_MAX   = 0.4;

void main() {
    // clip circle (balle)
    
    if (u_circleMask && distance(v_worldPos, u_ballCenter) > u_radius) discard;

    // calcul UV pour world-map
    vec2 uvOffset = -2.0 * (u_ballCenter / u_tileSize);
    vec2 uv = v_uvWorld + uvOffset;
    uv.y = 1.0 - uv.y;

    // sample de la texture
    vec4 texcol = texture2D(u_texture, uv);

    // chroma-key : si pixel trop vert (et sans rouge/bleu), on le jette
    if (texcol.g > GREEN_THRESHOLD
        && texcol.r < NON_GREEN_MAX
        && texcol.b < NON_GREEN_MAX) {
        discard;
    }

    // rendu final avec la couleur du sprite (v_color)
    gl_FragColor = v_color * texcol;
}
