package org.subquark.growing_blob;

public class Cell {
    private CellContent cellContent;

    public void setCellContent(CellContent newContent) {
        this.cellContent = newContent;
    }

    public CellContent getContent() { return cellContent; }
    public boolean isEmpty() {
        return cellContent == null;
    }
}
