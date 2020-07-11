package sommarengine.events;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

public class EventExecutor {
    public <T extends Event> void call(Event event, Function<T, Boolean> callback) {
        try {
            boolean handled = callback.apply((T) event);
            event.setHandled(handled);
        } catch(ClassCastException e) {
            return;
        }
    }
}
