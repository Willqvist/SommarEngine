package sommarengine.platform;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;
import sommarengine.input.Input;
import sommarengine.platform.opengl.OpenGLInput;

public class SystemAPI {

    private static Callback debug;

    public static Input getSystemInput() {
        return new OpenGLInput();
    }

    public static void enableDebug() {
        debug = GLUtil.setupDebugMessageCallback();
    }

    public static void destroy() {
        if(debug != null)
            debug.free();
        GL.setCapabilities(null);

    }


}
