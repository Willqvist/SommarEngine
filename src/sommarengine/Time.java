package sommarengine;

import org.lwjgl.glfw.GLFW;

public class Time {
    public static double deltaTime = 0;
    public static double getTime() {
        return GLFW.glfwGetTime();
    }
}
