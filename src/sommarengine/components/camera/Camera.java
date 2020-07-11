package sommarengine.components.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import sommarengine.components.ComponentAdapter;
import sommarengine.core.Screen;
import sommarengine.core.Viewport;
import sommarengine.events.event_types.ViewportResizeEvent;


public class Camera extends ComponentAdapter {

    public enum ProjectionMode {
        ORTHOGRAPHIC, PERSPECTIVE;
    };
    private ProjectionMode mode = ProjectionMode.PERSPECTIVE;
    private float fov = 70, zNear = 0.1f, zFar = 1000f;
    private Matrix4f projectionMatrix,viewMatrix = new Matrix4f().identity(), vpm = new Matrix4f();
    private Vector3f prevPos = new Vector3f(0,0,0), prevRot = new Vector3f(0,0,0);
    private Viewport viewport = Screen.viewport;

    public Camera() {
        viewport.onResizeListener(this::onViewportResize);
        updateProjection();
    }

    public float getFov() {
        return fov;
    }

    public ProjectionMode getMode() {
        return mode;
    }

    public Camera setMode(ProjectionMode mode) {
        this.mode = mode;
        return this;
    }

    private boolean onViewportResize(ViewportResizeEvent event) {
        System.out.println("window resize: ");
        updateProjection();
        return false;
    }

    public void setViewport(Viewport viewport) {

        updateProjection();
        viewport.onResizeListener(this::onViewportResize);
    }

    private void updateProjection() {
        System.out.println("window resize: " + viewport.getHeight() + " | " + viewport.getWidth());

        switch (mode){
            case PERSPECTIVE:
                projectionMatrix = new Matrix4f().perspective((float)Math.toRadians(fov),viewport.getWidth()/(viewport.getHeight()*1f),zNear,zFar);
                break;
            case ORTHOGRAPHIC:
                break;
        }
        vpm.set(projectionMatrix).mul(viewMatrix);
    }

    public Camera setFov(float fov) {
        this.fov = fov;
        setViewport(viewport);
        return this;
    }

    public Matrix4f getViewProjectionMatrix() {
        return projectionMatrix;
    }

    public float getzNear() {
        return zNear;
    }

    public float getzFar() {
        return zFar;
    }

    public Camera setzNear(float zNear) {
        this.zNear = zNear;
        updateProjection();
        return this;
    }

    public Camera setzFar(float zFar) {
        this.zFar = zFar;
        updateProjection();
        return this;
    }

    float x = 0;

    private boolean hasChanged(Vector3f old, Vector3fc newVec,float epsilon) {
        if(Math.abs(old.x-newVec.x()) > epsilon || Math.abs(old.y-newVec.y()) > epsilon || Math.abs(old.z-newVec.z()) > epsilon)
            return true;
        return false;
    }

    private float epsilon = 0.02f;

    public void update() {
        Vector3fc rotation = transform.getRotation();
        Vector3fc position = transform.getPosition();
        if (hasChanged(prevPos,position,epsilon) || hasChanged(prevRot,rotation,epsilon)) {
            System.out.println("moving camera");
            viewMatrix.identity().rotateX(-rotation.x()).rotateY(-rotation.y()).rotateZ(-rotation.z()).translate(-position.x(), -position.y(), -position.z());
            vpm.set(projectionMatrix).mul(viewMatrix);
            prevPos.set(position);
            prevRot.set(rotation);
        }
    }

    public Matrix4f getVPM() {
        return vpm;
    }
}
