package org.subquark.growing_blob;

public class Emitter {
    private Color color;
    private int level;

    public Emitter() {
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (!(0 <= level && level < 4)) {
            throw new IllegalArgumentException("level must be 0, 1, 2 or 3, not " + level);
        }
        this.level = level;
    }

    public void increaseLevel() {
        this.setLevel(this.level + 1);
    }
}
