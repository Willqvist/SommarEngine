package sommarengine.platform.opengl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;
import sommarengine.graphics.ArrayBuffer;
import sommarengine.graphics.Buffer;
import sommarengine.model.TransferAttributes;
import sommarengine.tool.Console;

import java.lang.reflect.Array;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OpenGLBuffer implements Buffer{

    private static HashMap<Integer, Integer> boundBuffers = new HashMap<>();

    private int bufferId, dataPerVertex = 1, size;
    private int type;
    private int storage;
    private List<Integer> attribArrays = new ArrayList<>();
    private int vertexCount = 0;
    private ArrayBuffer parent;
    public OpenGLBuffer(ArrayBuffer parent, float[] data, int size, Type type, Store storage) {
        this.type = toOpenGLType(type);
        this.storage = toOpenGLStore(storage);
        this.size = size;
        bufferId = GL15.glGenBuffers();
        insideBind(true);
        if(data != null) {
            FloatBuffer buffer = ToBuffer(data, size);
            GL15.glBufferData(this.type, data, this.storage);
            memFree(buffer);
        } else {
            GL15.glBufferData(this.type, size*4, this.storage);
        }
        vertexCount = size;
        this.parent = parent;
    }

    private int toOpenGLType(Type type) {
        switch (type) {
            case ARRAY: return GL15.GL_ARRAY_BUFFER;
            case ELEMENT: return GL15.GL_ELEMENT_ARRAY_BUFFER;
        }
        return -1;
    }

    private int toOpenGLStore(Store storage) {
        switch (storage) {
            case STATIC: return GL15.GL_STATIC_DRAW;
            case DYNAMIC: return GL15.GL_DYNAMIC_DRAW;
        }
        return -1;
    }

    private FloatBuffer ToBuffer(float[] data, int size) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(size);
        float[] newData = new float[size];
        System.arraycopy(data,0,newData,0,size);
        buffer.put(newData).flip();
        return buffer;
    }

    private void memFree(FloatBuffer data) {
        if (data != null)
            MemoryUtil.memFree(data);
    }

    @Override
    public void bind() {
        insideBind(false);
    }

    private void insideBind(boolean inside) {
        if(parent != null && !inside)
            throw new Error("Cant bind a Buffer that is bound to a ArrayBuffer");
        if(boundBuffers.containsKey(type) && boundBuffers.get(type) == bufferId) return;
        boundBuffers.put(type,bufferId);
        GL15.glBindBuffer(type, bufferId);
    }

    @Override
    public void unbind() {

    }

    @Override
    public void setData(float[] data, int size) {
        if(storage != GL15.GL_DYNAMIC_DRAW) return;
        FloatBuffer buffer = ToBuffer(data, size);
        insideBind(true);
        GL15.glBufferSubData(type,0, buffer);
        memFree(buffer);
        vertexCount = size / dataPerVertex;

    }

    @Override
    public void setDataPerVertex(int amount) {
        dataPerVertex = amount;
        vertexCount = size / dataPerVertex;
    }

    @Override
    public int getVertexCount() {
        return vertexCount;
    }

    @Override
    public void destroy() {
        GL15.glDeleteBuffers(bufferId);
    }
}
