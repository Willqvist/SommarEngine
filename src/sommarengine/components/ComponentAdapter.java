package sommarengine.components;

import sommarengine.core.GameObject;

public class ComponentAdapter implements Component {

    private GameObject object;
    protected Transform transform;
    private boolean active = true;

    @Override
    public void created(GameObject object) {
        this.transform = object.getTransform();
        this.object = object;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setAsFirst() {
        getGameObject().setComponentOrder(this,0);
    }

    @Override
    public void setActive(boolean val) {
        active = val;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public GameObject getGameObject() {
        return object;
    }

    @Override
    public Transform getTransform() {
        return getGameObject().getTransform();
    }
}
