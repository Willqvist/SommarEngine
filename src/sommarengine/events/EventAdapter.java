package sommarengine.events;

public abstract class EventAdapter implements Event {

    private boolean handled = false;

    @Override
    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    @Override
    public boolean isHandled() {
        return handled;
    }
}
