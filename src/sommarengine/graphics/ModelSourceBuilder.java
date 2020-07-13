package sommarengine.graphics;

import sommarengine.model.TransferAttributes;
import sommarengine.platform.GraphicsAPI;

public class ModelSourceBuilder {

    public static TransferAttributes[] POS_UV_ATTRIBS = {
            new TransferAttributes(0, 3),
            new TransferAttributes(1, 2)
    };

    public static TransferAttributes[] POS2D_UV_TEX_ATTRIBS = {
            new TransferAttributes(0, 2),
            new TransferAttributes(1, 2),
            new TransferAttributes(2,1)
    };

    public static ModelSource createCube(int size) {
        float[] data = {
          0,0,0,    0,0,
          0,0,0,    0,0,
          0,0,0,    0,0,
          0,0,0,    0,0,
        };
        ModelSource source = new ModelSource(data, data.length, POS_UV_ATTRIBS);
        source.setDrawType(GraphicsAPI.DrawType.QUAD);
        return source;
    }

}
