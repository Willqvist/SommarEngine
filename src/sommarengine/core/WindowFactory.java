package sommarengine.core;

import sommarengine.platform.windows.WindowsWindow;

public class WindowFactory {

    public static Window createWindow(int width,int height, String title) {
        return new WindowsWindow(width, height, title);
    }

}
