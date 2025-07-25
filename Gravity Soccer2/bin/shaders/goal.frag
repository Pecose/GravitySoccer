#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;

// 0 = off, 1 = on
uniform int   u_chroma;
uniform vec3  u_keyColor;    // ex: vec3(0.0, 1.0, 0.0)
uniform float u_thresh;      // ex: 0.6
uniform float u_smooth;      // ex: 0.1

// pour assombrir / éclaircir facilement (ombre)
uniform float u_alphaMul;    // ex: 1.0 pour normal, 0.8 pour foncer un peu

void main() {
    vec4 tex = texture2D(u_texture, v_texCoords);

    if (u_chroma == 1) {
    	float dist = length(tex.rgb - u_keyColor);           // 0 = vert pur
    	float mask = smoothstep(u_thresh - u_smooth,          // plus petit => commence à couper
                            	u_thresh,                     // plus grand  => totalement opaque
                            	dist);
    	tex.a *= mask;
    	if (tex.a <= 0.0) discard;
	}


    tex.a *= u_alphaMul;

    gl_FragColor = tex * v_color;
}
