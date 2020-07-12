package sommarengine.platform;

import org.lwjgl.opengl.GL11;
import sommarengine.graphics.ArrayBuffer;
import sommarengine.graphics.Buffer;
import sommarengine.graphics.RenderingCommand;
import sommarengine.graphics.ShaderProgram;
import sommarengine.model.GpuDataTransfer;
import sommarengine.model.TransferAttributes;
import sommarengine.platform.opengl.*;
import sommarengine.texture.Texture;
import sommarengine.texture.TextureLoader;

import java.nio.ByteBuffer;

public class GraphicsAPI {

    public enum DrawType {
        TRIANGLE, QUAD
    }
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

    public static Buffer createBuffer(float[] data, int size, Buffer.Type type, Buffer.Store storage) {
        return new OpenGLBuffer(null ,data, size, type, storage);
    }

    public static Buffer createBuffer(Buffer.Type type) {
        return new OpenGLBuffer(null ,null, 0, type, Buffer.Store.DYNAMIC);
    }

    public static Buffer createBuffer(int size, Buffer.Type type) {
        return new OpenGLBuffer(null ,null, size, type, Buffer.Store.DYNAMIC);
    }

    public static ArrayBuffer createArrayBuffer() {
        return new OpenGLArrayBuffer();
    }

    private static int toOpenGLType(DrawType type) {
        switch (type) {
            case QUAD: return GL11.GL_QUADS;
            case TRIANGLE: return GL11.GL_TRIANGLES;
        }
        return -1;
    }

    public static void drawBuffer(Buffer buffer, DrawType type) {
        buffer.bind();
        GL11.glDrawArrays(toOpenGLType(type), 0, buffer.getVertexCount());
    }

    public static void drawArrayBuffer(ArrayBuffer buffer,int vertexCount, DrawType type) {
        buffer.bind();
        GL11.glDrawArrays(toOpenGLType(type), 0, vertexCount);
    }

}
