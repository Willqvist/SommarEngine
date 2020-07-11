package sommarengine.batching;

import sommarengine.texture.Texture;

import java.util.List;

public interface BatchSource {

    void onBatchFlush(Texture[] texture,int textureSize, float[] data, int size);

}
