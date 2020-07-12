package sommarengine.core;

public class EngineAttributes {

    private boolean debug = false,vsync=true;
    private int width=1080,height=720;
    private String title = "SommerEngine Project";


    public EngineAttributes setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isVsync() {
        return vsync;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public EngineAttributes setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public EngineAttributes setVsync(boolean vsync) {
        this.vsync = vsync;
        return this;
    }

    public EngineAttributes setWidth(int width) {
        this.width = width;
        return this;
    }

    public EngineAttributes setHeight(int height) {
        this.height = height;
        return this;
    }
}
