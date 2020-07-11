package sommarengine.components;

import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.ArrayList;
import java.util.List;

public class Transform extends ComponentAdapter {
    private Transform parent = null;
    private List<Transform> children = new ArrayList<>();

    private Vector3f localPosition = new Vector3f(0,0,0), localRotation = new Vector3f(0,0,0), localScale = new Vector3f(1,1,1);
    private Vector3f position = new Vector3f(0,0,0), rotation = new Vector3f(0,0,0), scale = new Vector3f(1,1,1);

    private boolean moved=false,scaled=false,rotated=false;

    public Transform getParent() {
        return parent;
    }

    public void addAsChild(Transform transform) {
        if(transform == null) return;
        transform.parent = this;
        this.children.add(transform);
    }

    public void setAsParent(Transform transform) {
        parent = transform;
        if(transform != null)
            transform.children.add(this);
    }

    protected boolean hasMoved() {
        return moved;
    }

    protected boolean hasScaled() {
        return scaled;
    }

    protected boolean hasRotated() {
        return rotated;
    }

    public List<Transform> getChildren() {
        return children;
    }

    @Override
    public void update() {
        super.update();

        if(parent != null) {
            float x,y,z;
            if(parent.hasMoved()) {
                x = parent.getPosition().x() + localPosition.x;
                y = parent.getPosition().y() + localPosition.y;
                z = parent.getPosition().z() + localPosition.z;
                this.position.set(x, y, z);
                moved = true;
                parent.moved = false;
            }

            if(parent.hasRotated()) {
                x = parent.getRotation().x() + localRotation.x;
                y = parent.getRotation().y() + localRotation.y;
                z = parent.getRotation().z() + localRotation.z;
                this.rotation.set(x, y, z);
                rotated = true;
                parent.rotated = false;
            }

            if(parent.hasScaled()) {
                x = parent.getScale().x() + localScale.x;
                y = parent.getScale().y() + localScale.y;
                z = parent.getScale().z() + localScale.z;
                this.scale.set(x, y, z);
                scaled = true;
                parent.scaled = false;
            }
        }
        else {
            this.position = localPosition;
            this.rotation = localRotation;
            this.scale = localScale;
        }
    }

    public Vector3fc getLocalPosition() {
        return localPosition;
    }

    public Vector3fc getLocalRotation() {
        return localRotation;
    }

    public Vector3fc getLocalScale() {
        return localScale;
    }

    public final Vector3fc getPosition() {
        return position;
    }

    public Vector3fc getRotation() {
        return rotation;
    }

    public Vector3fc getScale() {
        return scale;
    }

    public void setPosition(float x,float y,float z) {
        this.localPosition.set(x,y,z);
        moved = true;
    }

    public void setRotation(float x,float y,float z) {
        this.localRotation.set(x,y,z);
        rotated = true;
    }

    public void setScale(float x,float y,float z) {
        this.localScale.set(x,y,z);
        scaled = true;
    }

    public void translate(float x,float y,float z) {
        this.localPosition.x += x;
        this.localPosition.y += y;
        this.localPosition.z += z;
        moved = true;
    }

    public void rotate(float x,float y,float z) {
        this.localRotation.x += x;
        this.localRotation.y += y;
        this.localRotation.z += z;
        this.localRotation.x = this.localRotation.x >= 0 ? this.localRotation.x : 360 + this.localRotation.x;
        this.localRotation.y = this.localRotation.y >= 0 ? this.localRotation.y : 360 + this.localRotation.y;
        this.localRotation.z = this.localRotation.z >= 0 ? this.localRotation.z : 360 + this.localRotation.z;
        rotated = true;
    }

    public void scale(float x,float y,float z) {
        this.localScale.x += x;
        this.localScale.y += y;
        this.localScale.z += z;
        scaled = true;
    }

}
