package sommarengine.platform.opengl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import sommarengine.graphics.ArrayBuffer;
import sommarengine.graphics.Buffer;
import sommarengine.model.TransferAttributes;
import sommarengine.tool.Console;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glGetError;

public class OpenGLArrayBuffer implements ArrayBuffer {

    private int vao = -1,stride;
    private static int boundModel = -1;
    private List<Integer> attribArrays = new ArrayList<>();
    private List<Buffer> createdBuffers = new ArrayList<>();
    public OpenGLArrayBuffer() {
        System.out.println("IM HERE");
        vao = GL30.glGenVertexArrays();

        bind();
    }

    @Override
    public void bind() {
        if (boundModel == vao) return;
        boundModel = vao;
        GL30.glBindVertexArray(vao);
        for (int val: attribArrays) {
            GL20.glEnableVertexAttribArray(val);
        }
    }

    @Override
    public Buffer createBuffer(int size, Buffer.Type type) {
        bind();
        Buffer buffer = new OpenGLBuffer(this, null, size, type, Buffer.Store.DYNAMIC);
        createdBuffers.add(buffer);
        return buffer;
    }

    @Override
    public void deleteBuffer(Buffer buffer) {

    }

    @Override
    public void attribPointer(TransferAttributes... attributes) {
        stride = 0;
        for(int i = 0; i < attributes.length; i++) {
            stride += attributes[i].size;
        }
        stride *= 4;
        int offset = 0;
        bind();

        for(int i = 0; i < attributes.length; i++){
            TransferAttributes attribute = attributes[i];
            attribArrays.add(attribute.getIndex());
            GL20.glVertexAttribPointer(attribute.getIndex(), attribute.size, GL11.GL_FLOAT, false, stride, offset * 4);

            offset += attribute.size;
        }
        if(boundModel == vao)
            boundModel = -1;
    }

    @Override
    public void unbind() {

    }
}
