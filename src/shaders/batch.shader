vertex:
#version 330 core
in vec2 position;
in vec2 uv;
in float textureIndex;
out vec2 t_uv;
out float v_textureIndex;
uniform mat4 transform;
uniform mat4 mvp;
void main(){
    gl_Position = mvp*transform*vec4(position,0.0,1.0);
    t_uv = uv;
    v_textureIndex = textureIndex;
}

fragment:
#version 150 core
out vec4 fragPosition;
in vec2 t_uv;
in float v_textureIndex;
uniform vec4 _color = vec4(1);
uniform sampler2D textures[32];
void main(){
    int texIndex = int(v_textureIndex);
    fragPosition = texture(textures[texIndex],t_uv);
}