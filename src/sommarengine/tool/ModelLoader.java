package sommarengine.tool;

import sommarengine.graphics.ModelSource;
import sommarengine.obj.builder.Build;
import sommarengine.obj.parser.Parse;

import java.io.IOException;

public class ModelLoader {
    public static ModelSource load(String file) {
        try {
            Build builder = new Build();
            Parse obj = new Parse(builder, file);
        } catch (IOException e) {
            return null;
        }
        return new ModelSource(null,1);
    }
}
