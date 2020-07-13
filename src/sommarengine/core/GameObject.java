package sommarengine.core;

import sommarengine.components.Component;
import sommarengine.components.ComponentAdapter;
import sommarengine.components.Model;
import sommarengine.components.Transform;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class GameObject {
    private List<Component> componentList = new ArrayList<>();
    private Transform transform;
    private String tag = null;
    private boolean active = true;

    protected GameObject(Transform parent) {
        this.transform = new Transform();
        this.transform.setAsParent(parent);
        this.transform.created(this);
    }

    public <T extends Component> T addComponent(Supplier<T> supplier) {
        T comp = supplier.get();
        comp.created(this);
        comp.start();
        componentList.add(comp);
        return comp;
    }

    public <T extends Component> T getComponent(Class<T> cls) {
        for (Component c : componentList) {
            if(cls.isInstance(c)) {
                return (T)c;
            }
        }
        return null;
    }

    public void update() {
        if(!active) return;
        transform.update();
        for (Component comp: componentList) {
            if(!comp.isActive()) continue;
            comp.update();
        }
        for (Transform transform: transform.getChildren()) {
            transform.getGameObject().update();
        }
    }

    public void render() {
        if(!active) return;
        for (Component comp: componentList) {
            if(!comp.isActive()) continue;
            comp.render();
        }

        for (Transform transform: transform.getChildren()) {
            transform.getGameObject().render();
        }
    }

    public static GameObject Instantiate() {
        return Instantiate(null);
    }

    public void setTag(String tag) {
        this.tag = tag;
        GameObjectProvider.getActiveGameObjectHolder().setTaggedGameObject(this);
    }

    public String getTag() {
        return tag;
    }

    public static GameObject Instantiate(Transform parent) {
        GameObject obj = new GameObject(parent);
        if(parent == null)
            GameObjectProvider.getActiveGameObjectHolder().addGameObject(obj);
        return obj;
    }

    public static GameObject findTag(String tag) {
        return GameObjectProvider.getActiveGameObjectHolder().getGameObjectWithTag(tag);
    }

    public Transform getTransform() {
        return transform;
    }

    public void setComponentOrder(Component component, int pos) {
        int index = componentList.indexOf(component);
        componentList.remove(index);
        this.componentList.add(pos,component);
    }
}
