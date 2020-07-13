package sommarengine.graphics;

import org.joml.Vector3f;
import sommarengine.components.Transform;

public interface Light{
    Transform getTransform();
    float getRadius();
}
