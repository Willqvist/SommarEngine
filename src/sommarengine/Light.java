package sommarengine;

import sommarengine.components.ComponentAdapter;
import sommarengine.components.Transform;

public class Light extends ComponentAdapter implements sommarengine.graphics.Light {

    private float radius = 1;

    public Light setRadius(float radius) {
        this.radius = radius;
        return this;
    }

    @Override
    public float getRadius() {
        return radius;
    }
}
