package sommarengine.input;

import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.lwjgl.glfw.GLFW;
import sommarengine.core.Window;

public abstract class Input {
    protected static final int PRESSED = 5;
    protected static final int DOWN = 6;
    protected static final int RELEASE = 6;
    protected static int[] keys = new int[1024];
    protected static int[] mouseButton = new int[3];
    public static boolean isKeyDown(int key) {
        return keys[key] == GLFW.GLFW_PRESS || keys[key] == GLFW.GLFW_REPEAT;
    }

    public static boolean isKeyPressed(int key) {
        return keys[key] == PRESSED;
    }

    public static boolean isMousePressed(int button) {
        return mouseButton[button] == PRESSED;
    }

    public static boolean isMouseDown(int button) {
        return mouseButton[button] == GLFW.GLFW_PRESS || mouseButton[button] == GLFW.GLFW_REPEAT;
    }

    protected static Vector2i mousePos = new Vector2i(0,0);

    public static Vector2ic getMousePosition() {
        return mousePos;
    }

    public abstract void onKeyEvent(Window window,int key, int scancode, int action, int mods);
    public abstract void onMouseEvent(Window window,int button, int action, int mods);
    public abstract void setMousePosition(int x,int y);
    public abstract void pollEvents();
}
