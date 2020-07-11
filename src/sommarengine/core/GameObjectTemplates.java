package sommarengine.core;

import org.joml.Vector3f;
import sommarengine.components.camera.Camera;

public class GameObjectTemplates {

    public static Camera createCamera() {
        GameObject obj = GameObject.Instantiate();
        return obj.addComponent(Camera::new);
    }

}
