package com.example.lab2_gles;

import android.opengl.GLES32;

public class myLab2Mode extends myWorkMode {
    protected int startVertexFan;
    protected int startVertexStrip;

    myLab2Mode() {
        super();
        myCreateScene();
    }

    protected void myCreateScene() {
        int size = 1000;
        arrayVertex = new float[size];

        // Heptagon renkleri
        float[] colorArray = new float[]{
                1, 0, 0, // Red
                1, 0.5f, 0, // Orange
                1, 1, 0,  // Yellow
                0, 1, 0,  // Green
                0, 1, 1,  // Cyan
                0, 0, 1, // Blue
                1, 0, 1,  // Violet
                1, 0, 0  // Red (loop)
        };

        // Heptagon tanımla
        startVertexFan = 0;
        int pos = 0;
        float xc = 0, yc = 0;
        int colorpos = 0;
        pos = addVertex(arrayVertex, pos, xc, yc, 0, 1.0f, 1.0f, 1.0f);

        for (int i = 0; i <= 7; i++) {
            float a = 6.2831853f * (float) i / 7;
            float x = xc + 0.7f * (float) Math.sin(a);
            float y = yc + 0.67f * 0.7f * (float) Math.cos(a);
            pos = addVertex(arrayVertex, pos, x, y, 0, colorArray[colorpos], colorArray[colorpos+1], colorArray[colorpos+2]);
            colorpos += 3;
        }

        // Gökkuşağı şeridi
        startVertexStrip = pos / 6;
        for (int i = 0; i < 7; i++) {
            float x = -1.0f + i * 0.33f;
            float r = colorArray[i * 3];
            float g = colorArray[i * 3 + 1];
            float b = colorArray[i * 3 + 2];
            pos = addVertex(arrayVertex, pos, x, -0.9f, 0, r, g, b);
            pos = addVertex(arrayVertex, pos, x, -0.7f, 0, r, g, b);
        }
    }

    private int addVertex(float[] dest, int pos, float x, float y, float z, float r, float g, float b) {
        dest[pos++] = x;
        dest[pos++] = y;
        dest[pos++] = z;
        dest[pos++] = r;
        dest[pos++] = g;
        dest[pos++] = b;
        return pos;
    }

    public void myCreateShaderProgram() {
        myCompileAndAttachShaders(myShadersLibrary.vertexShaderCode2, myShadersLibrary.fragmentShaderCode3);
        myVertexArrayBind2(arrayVertex, 6, "vPosition", 0, "vColor", 3 * 4);
    }

    public void myUseProgramForDrawing(int width, int height) {
        GLES32.glBindVertexArray(VAO_id);
        GLES32.glDrawArrays(GLES32.GL_TRIANGLE_FAN, startVertexFan, 9);
        GLES32.glDrawArrays(GLES32.GL_TRIANGLE_STRIP, startVertexStrip, 14);
        GLES32.glBindVertexArray(0);
    }
}
