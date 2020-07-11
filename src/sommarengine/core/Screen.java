package sommarengine.core;

import sommarengine.events.Event;
import sommarengine.events.EventListener;
import sommarengine.events.event_types.ViewportResizeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Screen {
    public static final Resolution viewport = new Resolution(0,0);

    public static class Resolution implements Viewport, EventListener {
        int width, height;

        private List<Function<ViewportResizeEvent, Boolean>> listeners = new ArrayList<>();

        public Resolution(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        @Override
        public void onResizeListener(Function<ViewportResizeEvent, Boolean> callback) {
            listeners.add(callback);
        }

        @Override
        public void removeListener(Function<ViewportResizeEvent, Boolean> callback) {
            listeners.remove(callback);
        }

        public Resolution setWidth(int width) {
            this.width = width;
            return this;
        }

        public Resolution setHeight(int height) {
            this.height = height;
            return this;
        }

        private boolean onViewportResizeEvent(ViewportResizeEvent resize) {
            this.width = resize.width;
            this.height = resize.height;
            listeners.forEach(call->call.apply(resize));
            return false;
        }

        @Override
        public void onEvent(Event event) {
            Event.executor.call(event,this::onViewportResizeEvent);
        }
    }
}
