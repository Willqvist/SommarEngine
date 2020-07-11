package sommarengine.render;

import org.lwjgl.opengl.GL11;
import sommarengine.batching.Batch2D;
import sommarengine.batching.BatchSource;
import sommarengine.components.Sprite;
import sommarengine.components.Transform;
import sommarengine.components.camera.Camera;
import sommarengine.graphics.Shader;
import sommarengine.graphics.ShaderProvider;
import sommarengine.graphics.material.Material;
import sommarengine.model.GpuDataTransfer;
import sommarengine.model.TransferAttributes;
import sommarengine.platform.GraphicsAPI;
import sommarengine.shaders.BatchShader;
import sommarengine.shaders.DiffuseShader;
import sommarengine.texture.Texture;

import java.awt.*;
import java.util.List;


public class Renderer2D implements BatchSource {

    private Batch2D batch = new Batch2D(this, 10000);
    private Camera camera;
    private Material<BatchShader> material = new Material(Shader.create(BatchShader::new,ShaderProvider.createShader("batch.shader")));
    private Transform transform = new Transform();
    private int drawCalls = 0;

    private static Renderer2D renderer2D;

    private TransferAttributes[] attributes = new TransferAttributes[] {
            new TransferAttributes(TransferAttributes.TypeEnum.POSITION, 2),
            new TransferAttributes(TransferAttributes.TypeEnum.UV, 2),
            new TransferAttributes(TransferAttributes.TypeEnum.TEXTURE_ID,1)
    };

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
        //System.out.println("draw calls: " + drawCalls);
        //drawCalls = 0;
    }

    @Override
    public void onBatchFlush(Texture[] texture, int textureSize, float[] data, int size) {
        if(data.length == 0) return;
        drawCalls++;
        material.bind(transform, camera);
        for(int i = 0; i < textureSize; i++) {
            texture[i].bind(i);
        }
        GpuDataTransfer gpuObj = GraphicsAPI.transferData(data, size, true, false, attributes);
        gpuObj.bind();
        GL11.glDrawArrays(GL11.GL_QUADS, 0, gpuObj.getVertexCount());
    }
}
