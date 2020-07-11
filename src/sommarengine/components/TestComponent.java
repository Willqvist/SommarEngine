package sommarengine.components;

import org.lwjgl.glfw.GLFW;
import sommarengine.Time;
import sommarengine.input.Input;

import java.util.Random;

public class TestComponent extends ComponentAdapter {

    public float x = 0;

    @Override
    public void start() {
        super.start();
        int rand = (int) (Math.random() * 800) - 400;
        int rand2 = (int) (Math.random() * 800) - 400;
        this.transform.setPosition(0,0,0);
    }

    @Override
    public void update() {
        //x += 5 * Time.deltaTime;
        //getTransform().translate((float) (Math.sin(x) * 15),0,0);
        if(Input.isKeyDown(GLFW.GLFW_KEY_A)) {
            transform.rotate((float) (80*Time.deltaTime),0,0);
        }

        if(Input.isKeyDown(GLFW.GLFW_KEY_D)) {
            transform.rotate((float) (-80*Time.deltaTime),0,0);
        }

        if(Input.isKeyPressed(GLFW.GLFW_KEY_R)) {
            System.out.println("IM HERE");
            Sprite s = getGameObject().getComponent(Sprite.class);
            s.setActive(!s.isActive());
        }
    }
}
