package sommarengine.platform.opengl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;
import sommarengine.model.GpuDataTransfer;
import sommarengine.model.TransferAttributes;

import java.nio.FloatBuffer;

public class OpenGLGpuDataTransfer implements GpuDataTransfer {

    private int vao = -1,vbo = -1;
    private int stride = 0;
    private int vertexCount = 0;
    private int[] attribArrays;
    private static int boundModel = 0;
    public OpenGLGpuDataTransfer(float[] data, int size,boolean dynamic, TransferAttributes... attributes) {
        attribArrays = new int[attributes.length];
        rebuild(data,size, dynamic, attributes);
    }

    private int createVAO(){
        int id = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(id);
        return id;
    }

    private int createVBO(float[] meshData,int size, boolean dynamic){
        int ID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, ID);
        FloatBuffer data = ToBuffer(meshData, size);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, dynamic ? GL15.GL_DYNAMIC_DRAW : GL15.GL_STATIC_DRAW);
        memFree(data);

        return ID;
    }

    private void memFree(FloatBuffer data) {
        if (data != null)
            MemoryUtil.memFree(data);
    }

    private FloatBuffer ToBuffer(float[] data, int size) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(size);
        float[] newData = new float[size];
        System.arraycopy(data,0,newData,0,size);
        buffer.put(newData).flip();
        return buffer;
    }

    public int getVertexCount(){
        return vertexCount;
    }

    @Override
    public void rebuild(float[] data, int size, boolean dynamic, TransferAttributes[] attributes) {
        if(vao != -1 && !dynamic)
            destroy();

        int pointer = 0;
        stride = 0;
        for(int i = 0; i < attributes.length; i++) {
            stride += attributes[i].size;
        }
        stride *= 4;
        if(!dynamic || vao == -1 ) {
            vao = createVAO();
            vbo = createVBO(data, size, dynamic);
        } else {
            changeData(vbo, data, size);
        }
        for(int i = 0; i < attributes.length; i++){
            TransferAttributes attribute = attributes[i];
            point(i,attribute.getIndex(), attribute.size, pointer);
            pointer += attribute.size;
        }
        vertexCount = (int) (size / (stride * 0.25f));
    }

    private void changeData(int vbo, float[] data, int size) {
        FloatBuffer buffer = ToBuffer(data, size);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER,0, buffer);
        //GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        memFree(buffer);
        vertexCount = (int) (size / (stride * 0.25f));
    }

    private void point(int index,int type,int size,int offset){
        attribArrays[index] = type;
        GL20.glVertexAttribPointer(type, size, GL11.GL_FLOAT, false, stride, offset * 4);
    }

    public void bind(){
        if (boundModel == vao) return;
        boundModel = vao;
        GL30.glBindVertexArray(vao);
        for (int i = 0; i < attribArrays.length; i++) {
            GL20.glEnableVertexAttribArray(attribArrays[i]);
        }
    }

    @Override
    public void unbind() {

    }

    public int getVao(){
        return vao;
    }

    @Override
    public void destroy() {
        GL30.glDeleteVertexArrays(vao);
        GL15.glDeleteBuffers(vbo);
        boundModel = -1;
    }
}
