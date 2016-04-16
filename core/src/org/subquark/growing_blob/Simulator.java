package org.subquark.growing_blob;

import java.util.List;

public class Simulator {
    private List<Emitter> leftEmitters;
    private List<Emitter> rightEmitters;
    private List<Emitter> topEmitters;
    private List<Emitter> bottomEmitters;

    private List<List<Cell>> rcCells;

    public void simulateTurn() {
        handleLeftEmitters();
    }

    private void handleLeftEmitters() {
        for(Emitter emitter : leftEmitters) {
            int row  = emitter.getRow();
            int collidedCol = -1;

            for ( int column = 0; column < 6; column ++) {
                Cell cell = getCell(row, column);
                if (!cell.isEmpty() ) {
                    System.out.println("Collision!");
                    collidedCol = column;
                    break;
                }
            }

            if (collidedCol == -1) {
                emitter.fireBullet(7, 0);
            }
        }
    }

    private Cell getCell(int row, int column) {
        return rcCells.get(row).get(column);
    }

    public List<Emitter> getLeftEmitters() {
        return leftEmitters;
    }

    public void setLeftEmitters(List<Emitter> leftEmitters) {
        this.leftEmitters = leftEmitters;
    }

    public List<Emitter> getRightEmitters() {
        return rightEmitters;
    }

    public void setRightEmitters(List<Emitter> rightEmitters) {
        this.rightEmitters = rightEmitters;
    }

    public List<Emitter> getTopEmitters() {
        return topEmitters;
    }

    public void setTopEmitters(List<Emitter> topEmitters) {
        this.topEmitters = topEmitters;
    }

    public List<Emitter> getBottomEmitters() {
        return bottomEmitters;
    }

    public void setBottomEmitters(List<Emitter> bottomEmitters) {
        this.bottomEmitters = bottomEmitters;
    }

    public List<List<Cell>> getRcCells() {
        return rcCells;
    }

    public void setRcCells(List<List<Cell>> rcCells) {
        this.rcCells = rcCells;
    }
}
