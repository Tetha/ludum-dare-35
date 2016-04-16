package org.subquark.growing_blob;

public class Cell {
    private World world;
    private CellContent cellContent;
    private int row;
    private int col;

    public void setCellContent(CellContent newContent) {
        if (this.cellContent != null) {
            this.cellContent.setCell(null);
        }
        this.cellContent = newContent;
        this.cellContent.setCell(this);
    }

    public CellContent getContent() { return cellContent; }
    public boolean isEmpty() {
        return cellContent == null;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
