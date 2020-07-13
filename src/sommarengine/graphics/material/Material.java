package sommarengine.graphics.material;

import sommarengine.components.Transform;
import sommarengine.components.camera.Camera;
import sommarengine.graphics.Shader;
import sommarengine.texture.Texture;
import sommarengine.tool.MatrixTools;

import java.util.ArrayList;

public class Material<T extends Shader> {

    private T shader;
    private Texture[] textures = new Texture[5];
    private int textureIndex = 0;

    public Material(T shader) {
        this.shader = shader;
    }

    public T getShader() {
        return shader;
    }

    public void addTexture(String name, Texture texture) {
        shader.getProgram().bind();
        shader.getProgram().setUniform(name,textureIndex);
        textures[textureIndex] = texture;
        textureIndex ++;
    }


    public void bind(Transform transform, Camera camera) {
        for (int i = 0; i < textures.length; i++) {
            Texture t = textures[i];
            if(t != null)
                t.bind(i);
        }
        shader.bind();
        shader.getProgram().setUniform("mvp",camera.getVPM());
        shader.getProgram().setUniform("transform", MatrixTools.createTransformMatrix(transform));
    }

    public void unbind() {
        shader.unbind();
    }

}
