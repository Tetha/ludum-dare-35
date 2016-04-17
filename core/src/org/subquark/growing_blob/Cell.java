package org.subquark.growing_blob;

public class Cell {
    private World world;
    private CellContent cellContent;
    private int row;
    private int col;

    private Runnable energyTransferTowardsRightObserver;
    private Runnable energyTransferTowardsLeftObserver;
    private Runnable energyTransferTowardsDownObserver;
    private Runnable energyTransferTowardsUpObserver;
    private Runnable scoreGeneratedObserver;
    private Runnable buildPointGainedObserver;

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

    public void onEnergyTransferredTowardsRight(Runnable observer) {
        this.energyTransferTowardsRightObserver = observer;
    }
    public void onEnergyTransferredTowardsLeft(Runnable observer) {
        this.energyTransferTowardsLeftObserver = observer;
    }
    public void onEnergyTransferredTowardsDown(Runnable observer) {
        this.energyTransferTowardsDownObserver = observer;
    }
    public void onEnergyTransferredTowardsUp(Runnable observer) {
        this.energyTransferTowardsUpObserver = observer;
    }

    public void notifyEnergyTransferredTowardsRight() {
        if (this.energyTransferTowardsRightObserver != null) {
            this.energyTransferTowardsRightObserver.run();
        }
    }
    public void notifyEnergyTransferredTowardsLeft() {
        if (this.energyTransferTowardsLeftObserver != null) {
            this.energyTransferTowardsLeftObserver.run();
        }
    }
    public void notifyEnergyTransferredTowardsDown() {
        if (this.energyTransferTowardsDownObserver != null) {
            this.energyTransferTowardsDownObserver.run();
        }
    }
    public void notifyEnergyTransferredTowardsUp() {
        if (this.energyTransferTowardsUpObserver != null) {
            this.energyTransferTowardsUpObserver.run();
        }
    }

    public void setScoreGeneratedObserver(Runnable scoreGeneratedObserver) {
        this.scoreGeneratedObserver = scoreGeneratedObserver;
    }

    public void setBuildPointGainedObserver(Runnable buildPointGainedObserver) {
        this.buildPointGainedObserver = buildPointGainedObserver;
    }

    protected void notifyScoreProduced() {
        if (this.buildPointGainedObserver != null) this.buildPointGainedObserver.run();
    }

    protected void notifyBuildPointProduced() {
        if (this.buildPointGainedObserver != null) this.buildPointGainedObserver.run();
    }
}
