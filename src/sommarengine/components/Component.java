package sommarengine.components;

import sommarengine.core.GameObject;
import sommarengine.core.GameObjectProvider;

public interface Component {

    void created(GameObject object);
    void start();
    void update();
    void render();
    void destroy();
    void setAsFirst();

    void setActive(boolean val);
    boolean isActive();

    GameObject getGameObject();
    Transform getTransform();

    default GameObject Instantiate() {
        return Instantiate(null);
    }

    default GameObject Instantiate(Transform parent) {
        return GameObject.Instantiate(parent);
    }

}
