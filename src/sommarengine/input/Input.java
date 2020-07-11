package sommarengine.input;

import org.lwjgl.glfw.GLFW;

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

    public abstract void pollEvents();
}
