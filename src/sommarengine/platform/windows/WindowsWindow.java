package sommarengine.platform.windows;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;
import sommarengine.core.Screen;
import sommarengine.core.WindowAdapter;
import sommarengine.events.event_types.WindowCloseEvent;
import sommarengine.events.event_types.ViewportResizeEvent;
import sommarengine.graphics.RenderingCommand;
import sommarengine.platform.GraphicsAPI;
import sommarengine.platform.opengl.OpenGLInput;

import java.awt.*;

import static org.lwjgl.system.MemoryUtil.NULL;

public class WindowsWindow extends WindowAdapter {

    private int width,height;
    private String title;
    private long window;
    private OpenGLInput input = new OpenGLInput();

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

        GLFW.glfwSetWindowSizeCallback(window, (w, width, height)-> {
            this.width = width;
            this.height = height;
            resizeEvent.width = width;
            resizeEvent.height = height;
            sendEvent(resizeEvent);
            GL11.glViewport(0,0,width,height);
        });

        GLFW.glfwSetMouseButtonCallback(window,(long window, int button, int action, int mods)->{
            input.invoke(window,button,action,mods);
        });
        GLFW.glfwSetKeyCallback(window, input);
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
        GLFW.glfwDestroyWindow(window);
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
