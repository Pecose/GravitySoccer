#ifdef GL_ES
precision mediump float;
#endif

// uniforms de SpriteBatch
uniform mat4 u_projTrans;
uniform vec2 u_tileSize;   // x=largeur, y=hauteur

attribute vec4 a_position;
attribute vec4 a_color;

varying vec4 v_color;
varying vec2 v_uvWorld;
varying vec2 v_worldPos;

void main(){
    v_color    = a_color;
    // on divise X et Y par leurs tailles respectives
    v_uvWorld  = a_position.xy / u_tileSize;
    v_worldPos = a_position.xy;
    gl_Position = u_projTrans * a_position;
}
