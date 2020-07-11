package sommarengine.components;

import sommarengine.Time;

public class Model extends ComponentAdapter {

    @Override
    public void update() {
        System.out.println("updating: " + Time.deltaTime);
    }
}
