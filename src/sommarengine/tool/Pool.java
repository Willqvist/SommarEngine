package sommarengine.tool;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class Pool<T> {
    private Set<T> pool = new HashSet<>();
    private int max;
    private int pulled = 0;
    private Supplier<T> supplier;
    public Pool(Supplier<T> supplier, int maxSize) {
        this.max = maxSize;
    }

    public synchronized T pull() {
        if(pool.isEmpty() && pulled < max) {
            T obj = supplier.get();
            pool.add(obj);
            return obj;
        } else if(pulled >= max && pool.isEmpty()) {
            return null;
        }
        pulled ++;
        T t = pool.iterator().next();
        pool.remove(t);
        return t;
    }

    public synchronized void restore(T t) {
        pulled --;
        pool.add(t);
    }

}
