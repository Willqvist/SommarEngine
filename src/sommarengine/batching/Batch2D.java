package sommarengine.batching;

import sommarengine.graphics.Buffer;
import sommarengine.platform.GraphicsAPI;
import sommarengine.texture.Texture;
import sommarengine.texture.TextureLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Batch2D implements Batch {

    private int maxVerts = 0, maxIndicies = 0;
    private BatchSource source;
    private float[] vertices;
    private int currentIndex = 0;
    private int textureIndex = 1;
    private Texture[] boundTextures = new Texture[32];

    private Buffer indiciesBuffer;

    public Batch2D(BatchSource source, int maxVerts) {
        this.maxVerts = maxVerts*4;
        this.source = source;
        vertices = new float[this.maxVerts];
        boundTextures[0] = TextureLoader.WHITE;
        /*
        float indicies[] = new float[this.maxIndicies];
        int offset = 0;
        for(int i = 0; i < indicies.length; i+= 6) {
            indicies[i + 0] = 0 + offset;
            indicies[i + 1] = 1 + offset;
            indicies[i + 2] = 2 + offset;

            indicies[i + 3] = 2 + offset;
            indicies[i + 4] = 3 + offset;
            indicies[i + 5] = 0 + offset;

            offset += 4;
        }

        indiciesBuffer = GraphicsAPI.createBuffer(indicies,indicies.length, Buffer.Type.ELEMENT,Buffer.Store.STATIC);

         */

    }

    private void appendData(float ...data) {
        for (float dat: data) {
            vertices[currentIndex] = dat;
            currentIndex ++;
        }
    }

    private int boundTextureId(Texture texture) {
        int index = 0;
        for (Texture t: boundTextures) {
            if(index >= textureIndex) return 0;
            if(t.isSameTexture(texture)) {
                return index;
            }
            index ++;
        }
        return 0;
    }

    private boolean isOutsideRange() {
        return currentIndex+4*4 >= maxVerts;
    }
    //TODO: implementer texturesloat array to store multiple texture to minimize draw calls.
    public void submit(Texture texture, Color color, int x, int y, int width, int height, float rotate) {
        int textureIndex = boundTextureId(texture);
        if(textureIndex == 0) {
            boundTextures[this.textureIndex] = texture;
            textureIndex = this.textureIndex;
            this.textureIndex++;
        }

        if(isOutsideRange() || textureIndex > 31) {
            flush();
            if(textureIndex > 31) {
                this.textureIndex = 1;
                textureIndex = 1;
            }
        }
        double rad = Math.toRadians(rotate);
        float w = width/2;
        float h = height/2;
        float xOrg = x;
        float yOrg = y;

        float x1 = xOrg-w;
        float y1 = yOrg-h;
        float x2 = xOrg+w;
        float y2 = yOrg-h;
        float x3 = xOrg+w;
        float y3 = yOrg+h;
        float x4 = xOrg-w;
        float y4 = yOrg+h;

        if(rotate != 0) {


            float rightY = (float) (Math.sin(rad)*h);
            float rightX = (float) (Math.cos(rad)*w);
            float upY = (float) (Math.sin(rad+Math.PI/2)*h);
            float upX = (float) (Math.cos(rad+Math.PI/2)*w);

            x1 = xOrg-rightX-upX;
            y1 = yOrg-rightY-upY;

            x2 = xOrg+rightX-upX;
            y2 = yOrg+rightY-upY;

            x3 = xOrg+rightX+upX;
            y3 = yOrg+rightY+upY;

            x4 = xOrg-rightX+upX;
            y4 = yOrg-rightY+upY;

        }

        float uvWidth = texture.getWidth()/(texture.getOriginal().getWidth()*1f);
        float uvHeight = texture.getHeight()/(texture.getOriginal().getHeight()*1f);
        float uvOffsetX = texture.getOffsetX()/(texture.getOriginal().getWidth()*1f);
        float uvOffsetY = texture.getOffsetY()/(texture.getOriginal().getHeight()*1f);
        appendData(x1,y1,uvOffsetX,uvOffsetY+uvHeight,textureIndex+0.1f);
        appendData(x2,y2,uvOffsetX+uvWidth,uvOffsetY+uvHeight,textureIndex+0.1f);
        appendData(x3,y3,uvOffsetX+uvWidth,uvOffsetY,textureIndex+0.1f);
        appendData(x4,y4,uvOffsetX,uvOffsetY,textureIndex+0.1f);
    }

    public void end() {

    }

    public void flush() {
        source.onBatchFlush(boundTextures,textureIndex, vertices, currentIndex);
        currentIndex = 0;
        textureIndex = 1;
    }

}
