package org.subquark.growing_blob;

public abstract class CellContent {
    private final CellContentType type;
    private int energy;

    protected CellContent(CellContentType type) {
        this.type = type;
    }

    public CellContentType getType() {
        return type;
    }

    public int getEnergy() {
        return energy;
    }

    public void addEnergy(int amount) {
        this.energy += amount;

        if (this.energy > type.getMaxEnergy()) {
            throw new IllegalArgumentException("Cannot have more than " + type.getMaxEnergy() + "energy -current: " + energy);
        }
    }

    public void removeEnergy(int amount) {
        this.energy -= amount;

        if (this.energy < 0) {
            throw new IllegalStateException("Cannot have negative energy. Current: " + this.energy);
        }
    }
}
