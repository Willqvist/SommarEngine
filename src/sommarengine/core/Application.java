package sommarengine.core;

import sommarengine.components.camera.Camera;
import sommarengine.events.Event;
import sommarengine.events.EventListener;
import sommarengine.render.Renderer2D;
import sommarengine.scene.Scene;

import java.util.*;

public abstract class Application implements EventListener {

    private Stack<Layer> layers = new Stack<>();
    private Map<String, Scene> scenes = new HashMap<>();
    private Scene active;
    private Window window;
    private Camera mainCamera;
    protected final void init(int width,int height, String title){
        this.window = WindowFactory.createWindow(width, height, title);
        Scene s = new Scene();
        s.setAsActive();
        active = s;
        scenes.put("main",s);
    }

    public void addLayer(Layer layer) {
        this.layers = layers;
    }

    public Layer popLayer() {
        return this.layers.pop();
    }

    public abstract void start();

    public final void postStart() {
        GameObject object = GameObject.findTag("MainCamera");
        if(object != null) {
            mainCamera = object.getComponent(Camera.class);
            if(mainCamera != null)
                return;
        }
        mainCamera = GameObjectHierarchy.findFirstInstanceOfComponent(Camera.class);
        if(mainCamera == null) {
            throw new IllegalStateException("No camera found. please create one before starting!");
        }
    }

    public final void update() {
        this.window.update();
        this.onUpdate();
        layers.forEach(layer->layer.update());
        active.update();
    }

    public final void render() {
        this.window.clear();
        Renderer2D.getInstance().begin(mainCamera);
        active.render();
        Renderer2D.getInstance().end();
    }

    public final void destroy() {
        this.end();
        window.destroy();
    }

    protected Window getWindow() {
        return window;
    }

    protected void onUpdate() {}
    public void end() {}

    @Override
    public void onEvent(Event event) { }
}
