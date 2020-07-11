package sommarengine.platform.opengl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import sommarengine.texture.Texture;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class OpenGLTexture implements Texture {

    private int textureId, width, height, xOffset, yOffset;
    private OpenGLTexture parentTexture = null;
    private static int[] lastBoundTextures = new int[GL13.GL_TEXTURE31 - GL13.GL_TEXTURE0];


    private OpenGLTexture(int textureId,int xOffset,int yOffset, int width,int height) {
        this.textureId = textureId;
        this.width = width;
        this.height = height;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.parentTexture = this;

    }

    public static OpenGLTexture load(ByteBuffer buffer, int width, int height) {
        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        System.out.println("loading texture: " + textureID + " | " + width + " | " + height);
        return new OpenGLTexture(textureID,0,0, width, height);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getOffsetX() {
        return yOffset;
    }

    @Override
    public int getOffsetY() {
        return xOffset;
    }

    @Override
    public Texture getSubTexture(int xOffset, int yOffset, int width, int height) {
        OpenGLTexture texture = new OpenGLTexture(textureId,xOffset,yOffset,width,height);
        texture.parentTexture = parentTexture;
        return texture;
    }

    @Override
    public Texture getOriginal() {
        return parentTexture;
    }

    @Override
    public void bind() {
        bind(textureId, GL13.GL_TEXTURE0, GL_TEXTURE_2D);
    }

    @Override
    public void bind(int textureSlot) {
        bind(textureId, GL13.GL_TEXTURE0+textureSlot, GL_TEXTURE_2D);
    }

    private void bind(int texture, int tpos, int textureType) {
        if (lastBoundTextures[tpos - GL13.GL_TEXTURE0] == texture) return;
        GL13.glActiveTexture(tpos);
        lastBoundTextures[tpos-GL13.GL_TEXTURE0] = texture;
        GL11.glBindTexture(textureType, texture);
    }

    private void unbind(int tpos) {
        GL13.glActiveTexture(GL13.GL_TEXTURE0+tpos);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,0);
        lastBoundTextures[tpos] = -1;
    }

    @Override
    public void unbind() {
        unbind(GL13.GL_TEXTURE0);
    }

    @Override
    public boolean isSameTexture(Texture texture) {
        return false;
    }
}
