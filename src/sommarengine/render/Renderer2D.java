package sommarengine.render;

import org.lwjgl.opengl.GL11;
import sommarengine.batching.Batch2D;
import sommarengine.batching.BatchSource;
import sommarengine.components.Transform;
import sommarengine.components.camera.Camera;
import sommarengine.graphics.ArrayBuffer;
import sommarengine.graphics.Buffer;
import sommarengine.graphics.Shader;
import sommarengine.graphics.ShaderProvider;
import sommarengine.graphics.material.Material;
import sommarengine.model.GpuDataTransfer;
import sommarengine.model.TransferAttributes;
import sommarengine.platform.GraphicsAPI;
import sommarengine.shaders.BatchShader;
import sommarengine.texture.Texture;
import sommarengine.tool.Console;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glGetError;


public class Renderer2D implements BatchSource {

    private static final int MAX_QUADS = 20000;

    private Batch2D batch = new Batch2D(this, MAX_QUADS);
    private Camera camera;
    private Material<BatchShader> material = new Material(Shader.create(BatchShader::new,ShaderProvider.createShader("batch.shader")));
    private Transform transform = new Transform();
    private int drawCalls = 0;

    private static Renderer2D renderer2D;
    private Buffer vertexBuffer;
    private ArrayBuffer arrayBuffer;

    private TransferAttributes[] attributes = new TransferAttributes[] {
            new TransferAttributes(0, 2),
            new TransferAttributes(1, 2),
            new TransferAttributes(2,1)
    };

    protected Renderer2D() {
        arrayBuffer = GraphicsAPI.createArrayBuffer();
        vertexBuffer = arrayBuffer.createBuffer(MAX_QUADS*5, Buffer.Type.ARRAY);
        vertexBuffer.setDataPerVertex(5);
        //arrayBuffer.bind();
        //vertexBuffer = GraphicsAPI.createBuffer(MAX_QUADS*5, Buffer.Type.ARRAY);

        arrayBuffer.attribPointer(attributes);
    }

    public static Renderer2D getInstance() {
        if(renderer2D == null)
            renderer2D = new Renderer2D();
        return renderer2D;
    }

    public void init() {}

    public void begin(Camera camera) {
        this.camera = camera;
    }

    public void render(Texture texture, Color color, int x, int y, int width, int height, float rotationX, float rotationY) {
        batch.submit(texture,color,x,y,width,height, rotationX);
    }

    public void end() {
        batch.flush();
        drawCalls = 0;
    }

    @Override
    public void onBatchFlush(Texture[] texture, int textureSize, float[] data, int size) {
        if(size == 0) return;
        drawCalls++;
        material.bind(transform, camera);
        for(int i = 0; i < textureSize; i++) {
            texture[i].bind(i);
        }

        arrayBuffer.bind();
        vertexBuffer.setData(data,size);
        GraphicsAPI.drawArrayBuffer(arrayBuffer,vertexBuffer.getVertexCount(), GraphicsAPI.DrawType.QUAD);
    }
}
