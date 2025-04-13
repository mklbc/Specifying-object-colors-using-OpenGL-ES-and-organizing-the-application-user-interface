package com.example.lab2_gles;

import android.opengl.GLES32;
import android.os.SystemClock;

public class myLab2AnimationMode extends myLab2Mode {

    public void myUseProgramForDrawing(int width, int height) {
        // Heptagon için ışık animasyonu
        long time = SystemClock.uptimeMillis() % 2000L;
        float vlight = (time <= 1000) ? time : 1999 - time;
        vlight *= 0.001f;

        // 1. Heptagon'u animasyonlu shader ile çiz
        GLES32.glUseProgram(gl_Program);
        int colorHandle = GLES32.glGetUniformLocation(gl_Program, "vLight");
        GLES32.glUniform1f(colorHandle, vlight);

        GLES32.glBindVertexArray(VAO_id);
        GLES32.glDrawArrays(GLES32.GL_TRIANGLE_FAN, startVertexFan, 9);
        GLES32.glBindVertexArray(0);

        // 2. Sabit shader'a geç, gökkuşağı şeridini çiz
        GLES32.glUseProgram(gl_ProgramStatic);
        GLES32.glBindVertexArray(VAO_id_static);
        GLES32.glDrawArrays(GLES32.GL_TRIANGLE_STRIP, startVertexStrip, 14);
        GLES32.glBindVertexArray(0);
    }

    public void myCreateShaderProgram() {
        // 1. Heptagon için animasyonlu shader
        myCompileAndAttachShaders(myShadersLibrary.vertexShaderCode2, myShadersLibrary.fragmentShaderCode4);
        myVertexArrayBind2(arrayVertex, 6, "vPosition", 0, "vColor", 3 * 4);

        // 2. Gökkuşağı şeridi için sabit renk shader'ı
        gl_ProgramStatic = myCompileAndAttachShadersStatic(myShadersLibrary.vertexShaderCode2, myShadersLibrary.fragmentShaderCode3);
        myVertexArrayBind2Static(arrayVertex, 6, "vPosition", 0, "vColor", 3 * 4);
    }
}
