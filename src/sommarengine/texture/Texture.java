package sommarengine.texture;

public interface Texture {
    int getWidth();
    int getHeight();
    int getOffsetX();
    int getOffsetY();
    Texture getSubTexture(int xOffset, int yOffset, int width,int height);
    Texture getOriginal();
    void bind();
    void bind(int textureSlot);
    void unbind();


    boolean isSameTexture(Texture texture);
}
