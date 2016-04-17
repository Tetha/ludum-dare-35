package org.subquark.growing_blob;

public abstract class CellContent {
    private final CellContentType type;
    private Cell cell;

    private int energy;
    private int incomingEnergy;

    private Runnable scoreGeneratedObserver;
    private Runnable buildPointGainedObserver;

    protected CellContent(CellContentType type) {
        this.type = type;
    }

    public CellContentType getType() {
        return type;
    }

    public int getEnergy() {
        return energy;
    }

    public int getEnergyForDisplay() { return energy + incomingEnergy; }

    public void addEnergy(int amount) {
        this.incomingEnergy += amount;

        if (this.energy > type.getMaxEnergy()) {
            throw new IllegalArgumentException("Cannot have more than " + type.getMaxEnergy() + "energy -current: " + energy);
        }
    }

    public void setEnergy(int amount) {
        this.energy = amount;
    }

    public void addParticleEnergy(int amount) {
        this.energy += amount;
        if (this.energy > type.getMaxEnergy()) this.energy = type.getMaxEnergy();
    }

    public void commitEnergy() {
        this.energy += incomingEnergy;
        incomingEnergy = 0;
        if (this.energy > type.getMaxEnergy()) this.energy = type.getMaxEnergy();
    }

    public void removeEnergy(int amount) {
        this.energy -= amount;

        if (this.energy < 0) {
            throw new IllegalStateException("Cannot have negative energy. Current: " + this.energy);
        }
    }

    public boolean canAcceptMoreEnergy() {
        return this.energy + this.incomingEnergy < type.getMaxEnergy();
    }

    public abstract void tick();

    protected Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
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
