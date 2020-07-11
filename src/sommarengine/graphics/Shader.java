package sommarengine.graphics;

import java.util.function.Supplier;

public interface Shader {
    static <T extends Shader> Shader create(Supplier<T> supplier, ShaderProgram program) {
        T s = supplier.get();
        s.useProgram(program);
        return s;
    }

    void useProgram(ShaderProgram program);
    void bind();
    void unbind();
    ShaderProgram getProgram();
}
