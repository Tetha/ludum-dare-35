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
        handleRightEmitters();
        handleTopEmitters();
        handleBottomEmitters();
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
                emitter.fireBullet(6, 0);
            } else {
                emitter.fireBullet(collidedCol, 0);
            }
        }
    }

    private void handleRightEmitters() {
        for(Emitter emitter : rightEmitters) {
            int row  = emitter.getRow();
            int collidedCol = -1;

            for ( int column = 5; 0 <= column; column --) {
                Cell cell = getCell(row, column);
                if (!cell.isEmpty() ) {
                    System.out.println("Collision!");
                    collidedCol = column;
                    break;
                }
            }

            if (collidedCol == -1) {
                emitter.fireBullet(6, 0);
            } else {
                emitter.fireBullet(collidedCol, 0);
            }
        }
    }

    private void handleTopEmitters() {
        for(Emitter emitter : topEmitters) {
            int col  = emitter.getColumn();
            int collidedRow = -1;

            for ( int row = 5; 0 <= row; row --) {
                Cell cell = getCell(row, col);
                if (!cell.isEmpty() ) {
                    System.out.println("Collision!");
                    collidedRow = row;
                    break;
                }
            }

            if (collidedRow == -1) {
                emitter.fireBullet(0, 6);
            } else {
                emitter.fireBullet(0, collidedRow);
            }
        }
    }

    private void handleBottomEmitters() {
        for(Emitter emitter : bottomEmitters) {
            int col  = emitter.getColumn();
            int collidedRow = -1;

            for ( int row = 0; row < 6; row ++) {
                Cell cell = getCell(row, col);
                if (!cell.isEmpty() ) {
                    System.out.println("Collision!");
                    collidedRow = row;
                    break;
                }
            }

            if (collidedRow == -1) {
                emitter.fireBullet(0, 6);
            } else {
                emitter.fireBullet(0, collidedRow);
            }
        }
    }


    public Cell getCell(int row, int column) {
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
