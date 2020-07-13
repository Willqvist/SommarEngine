package sommarengine.graphics;

import sommarengine.model.TransferAttributes;
import sommarengine.platform.GraphicsAPI;

import java.util.stream.Stream;

public class ModelSource {

    private ArrayBuffer arrayBuffer;
    private Buffer buffer;
    private GraphicsAPI.DrawType drawType = GraphicsAPI.DrawType.TRIANGLE;
    public ModelSource(float[] data, int size, TransferAttributes... attributes) {
        arrayBuffer = GraphicsAPI.createArrayBuffer();
        buffer = arrayBuffer.createBuffer(data, size, Buffer.Type.ARRAY);
        buffer.setDataPerVertex(Stream.of(attributes).mapToInt((s)->s.size).reduce(0,(l,r)->l+r));
    }

    public void setDrawType(GraphicsAPI.DrawType type) {
        drawType = type;
    }

    public void bind() {
        arrayBuffer.bind();
    }

    public void render() {
        arrayBuffer.bind();
        GraphicsAPI.drawArrayBuffer(arrayBuffer, buffer.getVertexCount(), drawType);
    }

}
