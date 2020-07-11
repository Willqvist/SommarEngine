package sommarengine.core;

import sommarengine.Time;
import sommarengine.events.Event;
import sommarengine.events.EventListener;
import sommarengine.events.event_types.WindowCloseEvent;

public class SommarEngine implements EventListener {

    private boolean isRunning = true;
    private Application application;

    public SommarEngine(Application application) {
        this.application = application;
        application.init(1080,720, "Title of window");
        application.getWindow().addEventListener(this);
        application.getWindow().addEventListener(Screen.viewport);
        loop();
    }

    private void loop() {
        application.start();
        application.postStart();
        double t = 0;
        final double dt = 1/60d;
        double prevFrameTime = Time.getTime();
        double accumulator = 0;
        double time = 0;
        double freeTime = 0;
        long last = System.currentTimeMillis();

        int udp = 0, fps = 0;

        while(isRunning) {
            time = Time.getTime();
            freeTime = time - prevFrameTime;
            prevFrameTime = time;
            accumulator += freeTime;
            while(accumulator >= dt) {
                Time.deltaTime = dt;
                application.update();
                udp ++;
                accumulator -= dt;
            }
            fps ++;
            application.render();

            if(System.currentTimeMillis() - last > 1000) {
                last = System.currentTimeMillis();
                System.out.println("Updates: " + udp + " Frames: " + fps);
                fps = 0;
                udp = 0;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        application.destroy();
    }

    private boolean onWindowClose(WindowCloseEvent event) {
        isRunning = false;
        return true;
    }

    @Override
    public void onEvent(Event event) {
        Event.executor.call(event, this::onWindowClose);
        application.onEvent(event);
    }
}
