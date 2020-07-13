package sommarengine.render;

import org.joml.Vector3fc;
import sommarengine.graphics.Light;
import sommarengine.tool.GridSort;

import java.util.List;

public class LightProvider {

    private static GridSort<Light> lights = new GridSort<>(32);

    public static List<Light> getLights(Vector3fc point, int numberOfLights) {
        return lights.getClosestItems(point,1,1, numberOfLights, (light, light2) -> {
            if(light.equals(light2)) return -1;
            return light.getTransform().getPosition().distanceSquared(point) < light2.getTransform().getPosition().distanceSquared(point) ? 0 : 1;
        });
    }

    public static void addLight(Light light) {
        lights.add(light,light.getTransform().getPosition(),light.getRadius());
    }

}
