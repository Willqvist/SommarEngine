package sommarengine.core;

import sommarengine.events.EventListener;

public interface Window {

    int getWidth();
    int getHeight();
    String getTitle();
    void show();
    void update();
    void clear();
    void destroy();
    void enableVsync(boolean enabled);
    void addEventListener(EventListener listener);

}
