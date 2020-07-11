package sommarengine.events.event_types;

import sommarengine.events.EventAdapter;
import sommarengine.events.EventType;

public class WindowCloseEvent extends EventAdapter {
    @Override
    public EventType getType() {
        return EventType.WINDOW_CLOSE;
    }
}
