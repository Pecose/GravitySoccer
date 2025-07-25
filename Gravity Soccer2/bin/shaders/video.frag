#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform vec2  u_texelSize;   // 1/width, 1/height
uniform float u_low;         // 0.05
uniform float u_high;        // 0.15
uniform float u_despill;     // 0.5

float rawMask(vec2 uv){
    vec4 c = texture2D(u_texture, uv);
    float chroma = c.g - max(c.r, c.b);              // vert dominant
    float r = smoothstep(u_low, u_high, chroma);     // vert -> 1
    return 1.0 - r;                                  // on inverse : vert -> 0
}

void main() {
    vec4 tex = texture2D(u_texture, v_texCoords);

    // masque + légère érosion (4 voisins)
    float m0 = rawMask(v_texCoords);
    vec2 px = u_texelSize;
    float m1 = rawMask(v_texCoords + vec2(px.x, 0.0));
    float m2 = rawMask(v_texCoords + vec2(-px.x, 0.0));
    float m3 = rawMask(v_texCoords + vec2(0.0, px.y));
    float m4 = rawMask(v_texCoords + vec2(0.0,-px.y));
    float mask = min(m0, min(m1, min(m2, min(m3, m4))));

    tex.a *= mask;
    if (tex.a <= 0.0) discard;

    // despill (atténue le vert restant)
    float spill   = 1.0 - mask;
    float neutral = (tex.r + tex.b) * 0.5;
    tex.g = mix(tex.g, neutral, u_despill * spill);

    gl_FragColor = tex * v_color;
}
