package sommarengine.components;

import sommarengine.Time;
import sommarengine.components.camera.Camera;
import sommarengine.graphics.ModelSource;
import sommarengine.graphics.material.Material;
import sommarengine.render.Renderer;

public class Model extends ComponentAdapter {

    private ModelSource source;
    private Material material;

    @Override
    public void update() {
        System.out.println("updating: " + Time.deltaTime);
    }

    public void setModel(ModelSource model) {
        this.source = source;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public void render() {
        Camera camera = Renderer.getCamera();
        Collider col = getGameObject().getComponent(Collider.class);
        if(col != null && camera.isInside(col))
            Renderer.render(source, this.getTransform(),material);
    }
}
