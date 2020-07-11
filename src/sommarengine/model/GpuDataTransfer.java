package sommarengine.model;

public interface GpuDataTransfer {
    void bind();
    void unbind();
    void destroy();

    int getVertexCount();

    void rebuild(float[] data, int size, boolean dyanmic, TransferAttributes[] attributes);
}
