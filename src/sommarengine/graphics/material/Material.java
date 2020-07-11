package sommarengine.graphics.material;

import sommarengine.components.Transform;
import sommarengine.components.camera.Camera;
import sommarengine.graphics.Shader;
import sommarengine.texture.Texture;
import sommarengine.tool.MatrixTools;

public class Material<T extends Shader> {

    private T shader;

    public Material(T shader) {
        this.shader = shader;
    }

    public T getShader() {
        return shader;
    }

    public void setTexture(Texture texture) {
        setTexture(0, texture);
    }

    public void setTexture(int order, Texture texture) {

    }

    public void setAlbedoTexture(Texture texture) {

    }

    public void setNormalTexture(Texture texture) {

    }

    public void setBumpmapTexture(Texture texture) {

    }


    public void bind(Transform transform, Camera camera) {
        shader.bind();
        shader.getProgram().setUniform("mvp",camera.getVPM());
        shader.getProgram().setUniform("transform", MatrixTools.createTransformMatrix(transform));
    }

    public void unbind() {
        shader.unbind();
    }

}
