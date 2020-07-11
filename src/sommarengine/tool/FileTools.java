package sommarengine.tool;

import org.lwjgl.BufferUtils;
import sommarengine.graphics.ShaderFiles;
import sommarengine.platform.GraphicsAPI;
import sommarengine.platform.opengl.OpenGLTexture;
import sommarengine.texture.Texture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class FileTools {

    private static ShaderFiles shaderFile = new ShaderFiles();
    public static ShaderFiles parseShaderFile(String file) {
        String fullPath = Paths.SHADER.getPath() + file;
        shaderFile.vertexShader = "";
        shaderFile.fragmentShader = "";

        File fileObj = new File(fullPath);
        try {

            int state = -1;

            FileReader fileReader = new FileReader(fileObj);
            BufferedReader reader = new BufferedReader(fileReader);
            StringBuilder builder = new StringBuilder();
            String line;
            while((line=reader.readLine()) != null) {
                if(line.equals("vertex:")) {
                    if(state == 1) {
                        shaderFile.fragmentShader = builder.toString();
                    }
                    state = 0;
                    builder = new StringBuilder();
                    continue;
                }else if(line.equals("fragment:")) {
                    if(state == 0) {
                        shaderFile.vertexShader = builder.toString();
                    }
                    state = 1;
                    builder = new StringBuilder();
                    continue;
                }
                builder.append(line + "\n");
            }
            String finalString = builder.toString();
            if(finalString.length() > 0) {
                if(state == 0) shaderFile.vertexShader = finalString;
                else if(state == 1) shaderFile.fragmentShader = finalString;
            }
        } catch (IOException e) {
            return null;
        }


        return shaderFile;
    }


    public static class GeneratedBuffer {
        public int width, height;
        public ByteBuffer buffer;
    }
    public static ByteBuffer getBuffer(BufferedImage image, float ox, float oy, float width, float height){
        int[] pixels = new int[(int)((image.getWidth()*width) * (image.getHeight()*height))];
        image.getRGB((int)(image.getWidth()*ox), (int)(image.getHeight()*oy), (int)(image.getWidth()*width), (int)(image.getHeight()*height), pixels, 0, (int)(image.getWidth()*width));
        ByteBuffer buffer = BufferUtils.createByteBuffer(pixels.length * 4);
        for (int i = 0; i < pixels.length; i++) {
            buffer.put((byte) ((pixels[i] >> 16) & 0xFF));
            buffer.put((byte) ((pixels[i] >> 8) & 0xFF));
            buffer.put((byte) (pixels[i] & 0xFF));
            buffer.put((byte) ((pixels[i] >> 24) & 0xFF));
        }
        buffer.flip();
        return buffer;
    }

    public static BufferedImage loadImage(String path) {
        BufferedImage image;
        try {
            System.out.println(path);
            image = ImageIO.read(FileTools.class.getResourceAsStream(path));
            return image;
        } catch (IOException e) {
            return null;
        }
    }

    public static Texture loadTexture(String path) {
        BufferedImage image = loadImage(Paths.RES.getPath()+path);
        if(image == null) return null;
        ByteBuffer buffer = getBuffer(image,0,0,1,1);
        return GraphicsAPI.loadTexture(buffer,image.getWidth(),image.getHeight());
    }

}
