package org.subquark.growing_blob;

public enum CellContentType {
    PARTICLE_ABSORBER(3), ROUTER(10);

    private final int maxEnergy;

    private CellContentType(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }
}
