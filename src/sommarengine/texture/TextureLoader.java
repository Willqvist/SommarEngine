package sommarengine.texture;

import sommarengine.platform.GraphicsAPI;
import sommarengine.platform.opengl.OpenGLTexture;
import sommarengine.texture.Texture;
import sommarengine.tool.FileTools;
import sommarengine.tool.Paths;

import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class TextureLoader {

    public static Texture WHITE = null;
    private static HashMap<String, Texture> loadedTextures = new HashMap<>();

    public static Texture load(String path) throws FileNotFoundException {
        if(loadedTextures.containsKey(path)) return loadedTextures.get(path);
        Texture t = FileTools.loadTexture(path);
        loadedTextures.put(path,t);

        return t;
    }

}
