package sommarengine.graphics;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public interface ShaderProgram {

    void bind();
    void setUniform(String name, int value);
    void setUniform(String name, float value);
    void setUniform(String name, boolean value);
    void setUniform(String name, Matrix4f value);
    void setUniform(String name, Vector4f value);
    void setUniform(String name, Vector3f value);
    void setUniform(String name, Vector2f value);
    void unbind();

    void bindAttribute(int i, String uv);

    void destroy();
}
