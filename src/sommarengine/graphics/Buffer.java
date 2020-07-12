package sommarengine.graphics;

import sommarengine.model.TransferAttributes;

public interface Buffer extends VertexHolder {

    void bind();
    void unbind();
    void setData(float[] data,int size);
    void setDataPerVertex(int amount);
    int getVertexCount();
    void destroy();

    enum Store {
        STATIC,DYNAMIC,STREAM
    }

    enum Type {
        ARRAY, ELEMENT
    }
}
