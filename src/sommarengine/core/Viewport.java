package sommarengine.core;

import sommarengine.events.event_types.ViewportResizeEvent;

import java.util.function.Function;

public interface Viewport {
    int getWidth();
    int getHeight();
    void onResizeListener(Function<ViewportResizeEvent, Boolean> callback);
    void removeListener(Function<ViewportResizeEvent, Boolean> callback);
}
