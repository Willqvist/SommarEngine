package sommarengine.shaders;

import org.joml.Vector3f;
import sommarengine.graphics.ShaderProgram;
import sommarengine.model.TransferAttributes;

import java.awt.*;

public class BatchShader extends ShaderAdapter {

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
        program.bindAttribute(TransferAttributes.TypeEnum.POSITION.getIndex(),"position");
        program.bindAttribute(TransferAttributes.TypeEnum.UV.getIndex(),"uv");
        program.bindAttribute(TransferAttributes.TypeEnum.TEXTURE_ID.getIndex(),"textureIndex");
        getProgram().bind();
        for(int i = 0; i < 32; i++) {
            program.setUniform("textures["+i+"]",i);
        }
    }
}
