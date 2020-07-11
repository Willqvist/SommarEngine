package sommarengine.components;

import org.joml.Vector3fc;
import sommarengine.render.Renderer2D;
import sommarengine.texture.Texture;
import sommarengine.texture.TextureLoader;

import java.awt.*;

public class Sprite extends ComponentAdapter {

    private Texture texture = TextureLoader.WHITE;
    private Color color = Color.WHITE;

    public void setSpriteTexture(Texture texture) {
        this.texture = texture;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void render() {
        super.render();
        Vector3fc scale = getGameObject().getTransform().getScale();
        Vector3fc position = getGameObject().getTransform().getPosition();
        Vector3fc rotation = getGameObject().getTransform().getRotation();
        Renderer2D.getInstance().render(texture,color,(int)position.x(),(int)position.y(),(int)scale.x(),(int)scale.y(),rotation.x(),rotation.y());
    }

    public void setSize(int width, int height) {
        this.transform.setScale(width,height,1);
    }
}
