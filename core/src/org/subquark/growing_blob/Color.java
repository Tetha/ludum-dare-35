package org.subquark.growing_blob;

public enum Color {
    RED(1, 0, 0), BLUE(0, 0, 1), YELLOW(1, 1, 0);

    private final float r;
    private final float g;
    private final float b;

    Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }
}
