package sommarengine.core;

import sommarengine.events.Event;
import sommarengine.events.EventListener;

import java.util.ArrayList;
import java.util.List;

public abstract class WindowAdapter implements Window {

    private List<EventListener> listeners = new ArrayList<EventListener>();

    @Override
    public void addEventListener(EventListener listener) {
        this.listeners.add(listener);
    }

    protected List<EventListener> getListeners() {
        return listeners;
    }

    protected void sendEvent(Event ev) {
        listeners.forEach(listener -> listener.onEvent(ev));
    }

}
