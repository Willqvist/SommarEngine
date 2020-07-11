package sommarengine.tool;

import java.util.HashSet;
import java.util.Set;

public class Pool<T> {
    private Set<T> pool = new HashSet<>();
    private int max;
    public Pool(int maxSize) {
        this.max = maxSize;
    }

    public synchronized T pull() {
        if(pool.isEmpty()) {
            return null;
        }
        T t = pool.iterator().next();
        pool.remove(t);
        return t;
    }

    public synchronized void restore(T t) {
        if(pool.size() >= max) return;
        pool.add(t);
    }

}
