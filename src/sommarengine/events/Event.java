package sommarengine.events;

public interface Event {

    EventExecutor executor = new EventExecutor();

    boolean isHandled();
    void setHandled(boolean handled);
    EventType getType();


}
