package sommarengine.tool;

public enum Paths {
    RES("/res/"),
    SHADER("src/shaders/"),
    MODEL("src/models/");

    String path;

    Paths(String path) {
        this.path = path;
    }

    public void setNewPath(String newPath) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
