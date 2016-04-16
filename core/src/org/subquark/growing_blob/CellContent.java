package org.subquark.growing_blob;

public abstract class CellContent {
    private final CellContentType type;

    protected CellContent(CellContentType type) {
        this.type = type;
    }

    public CellContentType getType() {
        return type;
    }
}
