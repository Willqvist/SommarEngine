package sommarengine.scene;

import sommarengine.core.GameObject;
import sommarengine.core.GameObjectHolder;
import sommarengine.core.GameObjectProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scene implements GameObjectHolder {

    private List<GameObject> objects = new ArrayList<>();
    private HashMap<String, GameObject> taggedObjects = new HashMap<>();
    @Override
    public boolean addGameObject(GameObject object) {
        objects.add(object);
        return true;
    }

    @Override
    public boolean removeGameObject(GameObject object) {
        return false;
    }

    @Override
    public void setTaggedGameObject(GameObject object) {
        taggedObjects.put(object.getTag(),object);
    }

    @Override
    public GameObject getGameObjectWithTag(String tag) {
        return null;
    }

    @Override
    public List<GameObject> getGameObjects() {
        return objects;
    }

    public void update() {
        for (GameObject obj: objects) {
            obj.update();
        }
    }

    public void render() {
        for (GameObject obj: objects) {
            obj.render();
        }
    }

    public void setAsActive() {
        GameObjectProvider.setAsActiveGameObjectHolder(this, true);
    }

}
