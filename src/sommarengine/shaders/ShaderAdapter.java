package sommarengine.shaders;

import sommarengine.graphics.Shader;
import sommarengine.graphics.ShaderProgram;

public abstract class ShaderAdapter implements Shader {

    private ShaderProgram program;

    @Override
    public void useProgram(ShaderProgram program) {
        this.program = program;
        program.bind();
        bindAttributes(program);
        program.unbind();
    }

    protected abstract void bindAttributes(ShaderProgram program);

    @Override
    public void bind() {
        this.program.bind();
    }

    @Override
    public void unbind() {
        this.program.unbind();
    }

    public ShaderProgram getProgram() {
        return program;
    }
}
