package sommarengine.tool;

import org.joml.Matrix4f;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import sommarengine.components.Transform;

public class MatrixTools {
    private static Matrix4f TRANSFORM = new Matrix4f();
    private static Matrix4f MVP = new Matrix4f();
    private static Matrix4f VP = new Matrix4f();


    public static Matrix4f createTransformMatrix(Transform transform) {
        return createTransformationMatrix(transform.getPosition(), transform.getRotation(), transform.getScale());
    }
    
    public static Matrix4f createTransformationMatrix(Vector3fc pos, Vector3fc rotation, Vector3fc scale) {
        return TRANSFORM.identity().translate(pos.x(), pos.y(), pos.z()).rotateX(rotation.x()).rotateY(rotation.y()).rotateZ(rotation.z()).scale(scale.x(), scale.y(), scale.z());
    }

    public static Matrix4f toMVP(Matrix4f model, Matrix4f view, Matrix4f projection){
        return toMVP(model,VP.identity().mul(projection).mul(view));
    }
    public static Matrix4f toMVP(Matrix4f model, Matrix4f vp){
        return MVP.identity().mul(vp).mul(model);
    }


}
