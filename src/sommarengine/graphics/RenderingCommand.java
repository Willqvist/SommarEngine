package sommarengine.graphics;

import sommarengine.platform.GraphicsAPI;
import sommarengine.platform.opengl.OpenGLRenderingCommand;

import java.awt.*;

public interface RenderingCommand {

    RenderingCommand instance = RenderingCommand.Instantiate();

    private static RenderingCommand Instantiate() {
        return GraphicsAPI.renderingCommand();
    }

    void clearScreen();
    void setClearColor(Color clearColor);
    void createContext();

}
