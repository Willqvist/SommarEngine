package sommarengine.core;

import java.util.List;

public interface GameObjectHolder {
    boolean addGameObject(GameObject object);
    boolean removeGameObject(GameObject object);

    void setTaggedGameObject(GameObject object);

    GameObject getGameObjectWithTag(String tag);

    List<GameObject> getGameObjects();
}
