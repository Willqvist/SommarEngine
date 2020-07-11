package sommarengine.platform;

import sommarengine.graphics.RenderingCommand;
import sommarengine.graphics.ShaderProgram;
import sommarengine.model.GpuDataTransfer;
import sommarengine.model.TransferAttributes;
import sommarengine.platform.opengl.OpenGLGpuDataTransfer;
import sommarengine.platform.opengl.OpenGLRenderingCommand;
import sommarengine.platform.opengl.OpenGLShaderProgram;
import sommarengine.platform.opengl.OpenGLTexture;
import sommarengine.texture.Texture;
import sommarengine.texture.TextureLoader;

import java.nio.ByteBuffer;

public class GraphicsAPI {


    private static GpuDataTransfer usedDataTransferObject = null;

    public static Texture loadTexture(ByteBuffer buffer, int width, int height) {
        return OpenGLTexture.load(buffer,width,height);
    }

    public static ShaderProgram createShader(String shaderPath) {
        return new OpenGLShaderProgram(shaderPath);
    }

    public static RenderingCommand renderingCommand() {
        return new OpenGLRenderingCommand();
    }

    public static void onRenderingContextSet() {
        byte[] data = {(byte) 255, (byte) 255, (byte) 255, (byte) 255};
        ByteBuffer buffer = ByteBuffer.allocateDirect(4).put(data);
        TextureLoader.WHITE = GraphicsAPI.loadTexture(buffer,1,1);
    }

    public static GpuDataTransfer transferData(float[] data, int size, boolean reuseObject, boolean dynamic, TransferAttributes ... attributes) {
        if(reuseObject) {
            if(usedDataTransferObject == null) {
                usedDataTransferObject = new OpenGLGpuDataTransfer(data, size,dynamic, attributes);
            } else {
                usedDataTransferObject.rebuild(data, size,dynamic, attributes);
            }
            return usedDataTransferObject;
        }
        return new OpenGLGpuDataTransfer(data, size,dynamic, attributes);
    }
}
