package sommarengine.events.event_types;

import sommarengine.events.EventAdapter;
import sommarengine.events.EventType;

public class ViewportResizeEvent extends EventAdapter {

    public int width,height;

    @Override
    public EventType getType() {
        return EventType.WINDOW_RESIZE;
    }


}
