package org.subquark.growing_blob;

import java.util.ArrayList;
import java.util.List;

public class Emitter {
    private Color color;
    private int level;

    private int row;
    private int column;

    private List<BulletFiredObserver> bulletFiredObservers = new ArrayList<>();

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

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void addBulletFiredObserver(BulletFiredObserver observer) {
        bulletFiredObservers.add(observer);
    }

    public void fireBullet(int rowsTravelled, int columnsTravelled) {
        for (BulletFiredObserver observer : bulletFiredObservers) {
            observer.onFired(rowsTravelled, columnsTravelled);
        }
    }

    @FunctionalInterface
    public interface BulletFiredObserver {
        void onFired(int rowsTravelled, int columnsTravelled);
    }
}
