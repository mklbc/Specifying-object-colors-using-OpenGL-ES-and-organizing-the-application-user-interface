package com.example.lab2_gles;

import android.opengl.GLES32;
import static android.opengl.GLES32.GL_COMPILE_STATUS;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

// The base class for various concrete OpenGL ES examples
public class myWorkMode {
    protected int gl_Program = 0;
    protected int VAO_id = 0;
    protected int VBO_id = 0;
    protected float[] arrayVertex = null;
    protected int numVertex = 0;
    protected int gl_ProgramStatic = 0;
    protected int VAO_id_static = 0;
    protected int VBO_id_static = 0;

    myWorkMode() {
        gl_Program = 0;
        VAO_id = VBO_id = 0;
    }

    public int getProgramId() {
        return gl_Program;
    }







    protected int myCompileAndAttachShadersStatic(String vsh, String fsh) {
        int shaderProgram = GLES32.glCreateProgram();

        int vertex_shader_id = myCompileShader(GLES32.GL_VERTEX_SHADER, vsh);
        if (vertex_shader_id == 0) return 0;

        int fragment_shader_id = myCompileShader(GLES32.GL_FRAGMENT_SHADER, fsh);
        if (fragment_shader_id == 0) return 0;

        GLES32.glAttachShader(shaderProgram, vertex_shader_id);
        GLES32.glAttachShader(shaderProgram, fragment_shader_id);
        GLES32.glLinkProgram(shaderProgram);
        GLES32.glDeleteShader(vertex_shader_id);
        GLES32.glDeleteShader(fragment_shader_id);

        return shaderProgram;
    }

    protected void myVertexArrayBind2Static(float[] src, int stride, String atrib1, int offset1, String atrib2, int offset2) {
        ByteBuffer bb = ByteBuffer.allocateDirect(src.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(src);
        vertexBuffer.position(0);

        getId_VAO_VBO_Static();

        GLES32.glBindVertexArray(VAO_id_static);
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, VBO_id_static);
        GLES32.glBufferData(GLES32.GL_ARRAY_BUFFER, src.length * 4, vertexBuffer, GLES32.GL_STATIC_DRAW);

        int handle = GLES32.glGetAttribLocation(gl_ProgramStatic, atrib1);
        GLES32.glEnableVertexAttribArray(handle);
        GLES32.glVertexAttribPointer(handle, 3, GLES32.GL_FLOAT, false, stride * 4, offset1);

        handle = GLES32.glGetAttribLocation(gl_ProgramStatic, atrib2);
        GLES32.glEnableVertexAttribArray(handle);
        GLES32.glVertexAttribPointer(handle, 3, GLES32.GL_FLOAT, false, stride * 4, offset2);

        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, 0);
        GLES32.glBindVertexArray(0);
    }

    protected void getId_VAO_VBO_Static() {
        int[] tmp = new int[2];
        GLES32.glGenVertexArrays(1, tmp, 0);
        VAO_id_static = tmp[0];
        GLES32.glGenBuffers(1, tmp, 0);
        VBO_id_static = tmp[0];
    }










    protected int myCompileShader(int shadertype, String shadercode) {
        int shader_id = GLES32.glCreateShader(shadertype);
        GLES32.glShaderSource(shader_id, shadercode);
        GLES32.glCompileShader(shader_id);

        // Check shader compiling errors
        int[] res = new int[1];
        GLES32.glGetShaderiv(shader_id, GL_COMPILE_STATUS, res, 0);
        if (res[0] != 1) return 0;
        return shader_id;
    }

    protected void myCompileAndAttachShaders(String vsh, String fsh) {
        gl_Program = 0;
        int vertex_shader_id = myCompileShader(GLES32.GL_VERTEX_SHADER, vsh);
        if (vertex_shader_id == 0) return;

        int fragment_shader_id = myCompileShader(GLES32.GL_FRAGMENT_SHADER, fsh);
        if (fragment_shader_id == 0) return;

        gl_Program = GLES32.glCreateProgram();
        GLES32.glAttachShader(gl_Program, vertex_shader_id);
        GLES32.glAttachShader(gl_Program, fragment_shader_id);
        GLES32.glLinkProgram(gl_Program);
        GLES32.glDeleteShader(vertex_shader_id);
        GLES32.glDeleteShader(fragment_shader_id);
    }

    protected void getId_VAO_VBO() {
        int[] tmp = new int[2];
        GLES32.glGenVertexArrays(1, tmp, 0);
        VAO_id = tmp[0];
        GLES32.glGenBuffers(1, tmp, 0);
        VBO_id = tmp[0];
    }

    protected void myVertexArrayBind(float[] src, String atrib) {
        ByteBuffer bb = ByteBuffer.allocateDirect(src.length * 4);
        bb.order(ByteOrder.nativeOrder());

        FloatBuffer vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(src);
        vertexBuffer.position(0);

        getId_VAO_VBO();

        // Bind the Vertex Array Object first
        GLES32.glBindVertexArray(VAO_id);

        // Then bind and set vertex buffer(s) and attribute pointer(s)
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, VBO_id);
        GLES32.glBufferData(GLES32.GL_ARRAY_BUFFER, src.length * 4, vertexBuffer, GLES32.GL_STATIC_DRAW);

        int handle = GLES32.glGetAttribLocation(gl_Program, atrib);
        GLES32.glEnableVertexAttribArray(handle);
        GLES32.glVertexAttribPointer(handle, 3, GLES32.GL_FLOAT, false, 3 * 4, 0);
        GLES32.glEnableVertexAttribArray(0);

        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, 0);
        GLES32.glBindVertexArray(0);
    }

    protected void myVertexArrayBind2(float[] src, int stride,
                                      String atrib1, int offset1,
                                      String atrib2, int offset2) {

        ByteBuffer bb = ByteBuffer.allocateDirect(src.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(src);
        vertexBuffer.position(0);
        getId_VAO_VBO();
// Bind the Vertex Array Object first
        GLES32.glBindVertexArray(VAO_id);
//then bind and set vertex buffer
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, VBO_id);
        GLES32.glBufferData(GLES32.GL_ARRAY_BUFFER, src.length * 4,
                vertexBuffer, GLES32.GL_STATIC_DRAW);

        int handle = GLES32.glGetAttribLocation(gl_Program, atrib1);
        GLES32.glEnableVertexAttribArray(handle);
        GLES32.glVertexAttribPointer(handle, 3, GLES32.GL_FLOAT,
                false, stride * 4, offset1);
        handle = GLES32.glGetAttribLocation(gl_Program, atrib2);
        GLES32.glEnableVertexAttribArray(handle);
        GLES32.glVertexAttribPointer(handle, 3, GLES32.GL_FLOAT,
                false, stride * 4, offset2);
        GLES32.glEnableVertexAttribArray(0);
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, 0);
        GLES32.glBindVertexArray(0);
    }





    public void myCreateShaderProgram() {
    }

    protected void myCreateScene() { }

    public void myUseProgramForDrawing(int width, int height) {
        // Default implementation for simply triangulated scenes
        GLES32.glBindVertexArray(VAO_id);
        GLES32.glDrawArrays(GLES32.GL_TRIANGLES, 0, numVertex);
        GLES32.glBindVertexArray(0);
    }
}
