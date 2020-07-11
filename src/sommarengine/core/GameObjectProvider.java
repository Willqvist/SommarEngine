package sommarengine.core;

public class GameObjectProvider {
    private static GameObjectHolder holder;

    public static void setAsActiveGameObjectHolder(GameObjectHolder holder, boolean clearOldHolder) {
        GameObjectProvider.holder = holder;
    }

    public static GameObjectHolder getActiveGameObjectHolder() {
        return holder;
    }
}
