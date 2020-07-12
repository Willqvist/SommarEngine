package sommarengine.platform.opengl;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;
import sommarengine.graphics.ShaderProgram;
import sommarengine.graphics.ShaderFiles;
import sommarengine.tool.FileTools;

import java.nio.FloatBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.glGetError;

public class OpenGLShaderProgram implements ShaderProgram {

    private int program, vertexShader, fragmentShader;

    private HashMap<String, Integer> cachedUniformLocations = new HashMap<>();

    public OpenGLShaderProgram(String shaderFile) {
        ShaderFiles shaderFiles = FileTools.parseShaderFile(shaderFile);
        createProgram();
        vertexShader = createShader(GL30.GL_VERTEX_SHADER, shaderFiles.vertexShader);
        fragmentShader = createShader(GL30.GL_FRAGMENT_SHADER, shaderFiles.fragmentShader);
        linkProgram();
    }

    private void createProgram() {
        program = GL30.glCreateProgram();
        //bind();
    }

    private int createShader(int shader, String src) {
        int shaderID = GL20.glCreateShader(shader);
        if (shaderID == 0)
            System.err.println("Could not load " + (shader == GL20.GL_VERTEX_SHADER ? "vertx" : "fragment") + " shader");
        GL20.glShaderSource(shaderID, src);
        GL20.glCompileShader(shaderID);

        int status = GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS);
        if (status == GL11.GL_FALSE)
            System.err.println("Error compiling shader code: " + GL20.glGetShaderInfoLog(shaderID, 1024));

        GL20.glAttachShader(program, shaderID);
        return shaderID;
    }

    private void linkProgram() {

        GL20.glLinkProgram(program);
        if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == 0)
            System.err.println("Error linking shader: " + GL20.glGetProgramInfoLog(program, 1024));

        if (vertexShader != 0) {
            GL20.glDetachShader(program, vertexShader);
            GL20.glDeleteShader(vertexShader);
        }
        if (fragmentShader != 0) {
            GL20.glDetachShader(program, fragmentShader);
            GL20.glDeleteShader(fragmentShader);
        }

        GL20.glValidateProgram(program);
        if (GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS) == 0)
            System.err.println("Error compiling shader: " + GL20.glGetProgramInfoLog(program, 1024));
    }

    private int getUniformLocation(String name) {
        if(cachedUniformLocations.containsKey(name))
            return cachedUniformLocations.get(name);

        int loc = GL20.glGetUniformLocation(program, name);
        cachedUniformLocations.put(name,loc);
        return loc;
    }

    @Override
    public void bind() {
        GL30.glUseProgram(program);
    }

    @Override
    public void setUniform(String name, int value) {
        GL20.glUniform1i(getUniformLocation(name), value);
    }

    @Override
    public void setUniform(String name, float value) {
        GL20.glUniform1f(getUniformLocation(name), value);
    }

    @Override
    public void setUniform(String name, boolean value) {
        setUniform(name, value?1:0);
    }

    @Override
    public void setUniform(String name, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            GL20.glUniformMatrix4fv(getUniformLocation(name), false, fb);
        }
    }

    @Override
    public void setUniform(String name, Vector4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(4);
            value.get(fb);
            GL20.glUniform4fv(getUniformLocation(name), fb);
        }
    }

    @Override
    public void setUniform(String name, Vector3f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(3);
            value.get(fb);
            GL20.glUniform3fv(getUniformLocation(name), fb);
        }
    }

    @Override
    public void setUniform(String name, Vector2f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(2);
            value.get(fb);
            GL20.glUniform2fv(getUniformLocation(name), fb);
        }
    }

    @Override
    public void unbind() {
        GL20.glUseProgram(0);
    }

    @Override
    public void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(program, attribute, variableName);
    }

    @Override
    public void destroy() {
        GL20.glDetachShader(program, vertexShader);
        GL20.glDetachShader(program, fragmentShader);
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
        GL20.glDeleteProgram(program);

    }
}
