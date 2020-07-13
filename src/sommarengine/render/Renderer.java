package sommarengine.render;

import sommarengine.components.Transform;
import sommarengine.components.camera.Camera;
import sommarengine.graphics.ModelSource;
import sommarengine.graphics.material.Material;
import sommarengine.platform.GraphicsAPI;

public class Renderer {

    private static Camera camera;

    public static void begin(Camera camera) {
        Renderer.camera = camera;
    }
    public static void render(ModelSource model, Transform transform, Material material) {
        material.bind(transform, camera);
        model.render();
    }
    public static void end() {}

    public static Camera getCamera() {
        return camera;
    }
}
