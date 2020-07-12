package sommarengine.platform.windows;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;
import sommarengine.core.Screen;
import sommarengine.core.WindowAdapter;
import sommarengine.events.event_types.WindowCloseEvent;
import sommarengine.events.event_types.ViewportResizeEvent;
import sommarengine.graphics.RenderingCommand;
import sommarengine.input.Input;
import sommarengine.platform.GraphicsAPI;
import sommarengine.platform.SystemAPI;
import sommarengine.platform.opengl.OpenGLInput;
import sommarengine.tool.Console;

import java.awt.*;

import static org.lwjgl.system.MemoryUtil.NULL;

public class WindowsWindow extends WindowAdapter {

    private int width,height;
    private String title;
    private long window;
    private Input input = SystemAPI.getSystemInput();

    private GLFWMouseButtonCallback mouseCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWWindowSizeCallback windowSizeCallback;

    public WindowsWindow(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        setup();
    }

    private void setup() {
        GLFWErrorCallback.createPrint(System.err).set();
        if(!GLFW.glfwInit())
            throw new IllegalStateException("GLFW was unable to initialize.");
        window = createWindow();

        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GLFW.GLFW_TRUE);
        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(window);
        RenderingCommand.instance.createContext();
        GraphicsAPI.onRenderingContextSet();
        RenderingCommand.instance.setClearColor(Color.BLACK);
        setupListeners();
    }

    private void setupListeners() {

        ViewportResizeEvent resizeEvent = new ViewportResizeEvent();
        resizeEvent.width = width;
        resizeEvent.height = height;
        Screen.viewport.onEvent(resizeEvent);

        GLFW.glfwSetWindowSizeCallback(window,windowSizeCallback = GLFWWindowSizeCallback.create(
        (w, width, height)-> {
            this.width = width;
            this.height = height;
            resizeEvent.width = width;
            resizeEvent.height = height;
            sendEvent(resizeEvent);
            GL11.glViewport(0,0,width,height);
        }));

        GLFW.glfwSetMouseButtonCallback(window,mouseCallback = GLFWMouseButtonCallback.create((long window, int button, int action, int mods)->{
            input.onMouseEvent(this,button,action,mods);
        }));
        GLFW.glfwSetKeyCallback(window, keyCallback = GLFWKeyCallback.create((window, key, scancode, action, mods)-> {
            input.onKeyEvent(this,key,scancode,action,mods);
        }));

    }

    public void show() {
        GLFW.glfwShowWindow(window);
    }

    @Override
    public void update() {
        if(GLFW.glfwWindowShouldClose(window)) {
            WindowCloseEvent e = new WindowCloseEvent();
            sendEvent(e);
            return;
        }
        input.pollEvents();
        GLFW.glfwPollEvents();
    }

    @Override
    public void clear() {
        GLFW.glfwSwapBuffers(window);
        RenderingCommand.instance.clearScreen();
    }

    @Override
    public void destroy() {
        GLFW.glfwSetErrorCallback(null).free();
        mouseCallback.free();
        keyCallback.free();
        windowSizeCallback.free();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    private long createWindow() {
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        long window = GLFW.glfwCreateWindow(width, height, title, NULL, NULL);
        if(window == NULL)
            throw new RuntimeException("Failed to create Window.");
        return window;
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
    public String getTitle() {
        return title;
    }

    @Override
    public void enableVsync(boolean enabled) {
        GLFW.glfwSwapInterval(enabled ? 1 : 0);
    }
}
