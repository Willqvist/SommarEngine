package sommarengine.core;

import sommarengine.components.Component;
import sommarengine.components.Transform;

import java.util.List;

public class GameObjectHierarchy {
    public static <T extends Component> T findFirstInstanceOfComponent(Class<T> comp) {
        List<GameObject> objects = GameObjectProvider.getActiveGameObjectHolder().getGameObjects();
        for (GameObject obj: objects) {
            T foundComp = findComp(obj,comp);
            if(foundComp != null)
                return foundComp;
        }
        return null;
    }

    private static <T extends Component> T findComp(GameObject parent, Class<T> comp) {
        T obj = parent.getComponent(comp);
        if(obj != null)
            return obj;

        for (Transform child: parent.getTransform().getChildren()) {
            obj = findComp(child.getGameObject(), comp);
            if(obj !=null)
                return obj;
        }
        return null;
    }

}
