package sommarengine.shaders;

import org.joml.Vector3f;
import sommarengine.graphics.ShaderProgram;

import java.awt.*;

public class DiffuseShader extends ShaderAdapter {

    private Vector3f colorVector = new Vector3f(0,0,0);

    public void setBaseColor(Color color) {
        this.getProgram().setUniform("_baseColor",
                colorVector.set(color.getRed()/255f,color.getGreen()/255f,color.getBlue()/255f));
    }

    public void setSpecular(float alpha) {
        this.getProgram().setUniform("_specular",alpha);
    }

    @Override
    protected void bindAttributes(ShaderProgram program) {
        program.bindAttribute(0,"position");
        program.bindAttribute(1,"uv");
    }
}
