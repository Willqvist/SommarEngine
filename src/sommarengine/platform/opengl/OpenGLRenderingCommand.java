package sommarengine.platform.opengl;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;
import sommarengine.graphics.RenderingCommand;

import java.awt.*;

public class OpenGLRenderingCommand implements RenderingCommand {

    public void clearScreen() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void setClearColor(Color color) {
        glClearColor(color.getRed()/255f,color.getGreen()/255f,color.getBlue()/255f,color.getAlpha()/255f);
    }

    public void createContext() {
        GL.createCapabilities();
    }
}
