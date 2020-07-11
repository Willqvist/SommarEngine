package sommarengine.graphics;

import sommarengine.platform.GraphicsAPI;

public class ShaderProvider {
    public static ShaderProgram createShader(String shaderPath) {
        return GraphicsAPI.createShader(shaderPath);
    }
}
