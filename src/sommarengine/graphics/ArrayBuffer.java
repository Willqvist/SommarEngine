package sommarengine.graphics;

import sommarengine.model.TransferAttributes;

public interface ArrayBuffer {
    void bind();
    Buffer createBuffer(int size, Buffer.Type type);
    Buffer createBuffer(float[] data, int size, Buffer.Type type);
    void deleteBuffer(Buffer buffer);
    void attribPointer(TransferAttributes... attributes);
    void unbind();
}
