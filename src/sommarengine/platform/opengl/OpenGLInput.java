package sommarengine.platform.opengl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import sommarengine.input.Input;

public class OpenGLInput extends Input implements GLFWKeyCallbackI {
    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        int mode = keys[key];
        if(mode == GLFW.GLFW_RELEASE && action == GLFW.GLFW_PRESS)
            action = PRESSED;
        keys[key] = action;
    }

    @Override
    public void pollEvents() {

        for(int i = 0; i < keys.length; i++) {
            if(keys[i] == RELEASE)
                keys[i] = GLFW.GLFW_RELEASE;
            if(keys[i] == PRESSED)
                keys[i] = GLFW.GLFW_PRESS;
        }


    }

    public void invoke(long window, int button, int action, int mods) {
        int mode = mouseButton[button];
        if(mode == GLFW.GLFW_RELEASE && action == GLFW.GLFW_PRESS)
            action = PRESSED;
        mouseButton[button] = action;
    }
}
