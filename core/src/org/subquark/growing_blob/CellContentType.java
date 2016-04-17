package org.subquark.growing_blob;

public enum CellContentType {
    PARTICLE_ABSORBER(10, 3,
                      "Particle Absorber",
            "The particle absorber collects\n"
           + "the particles Those are these\n"
           + "round things from the emitters\n"
           + "on the side\n"
           + "A transmitter can store up to\n"
           + "3 energy and transmit all that\n"
           + "in one turn"),
    ROUTER(5, 10, "Energy Router",
            "The energy router moves energy\n"
          + "around Each turn, the router\n"
          + "pushes up to 5 energy to random\n"
          + "adjacent cells\n"
          + "It can store up to 10 energy"),
    BUILD_POINT_GENERATOR(20, 5, "Buildpoint Generator",
            "The buildpoint generator consumes\n"
           + "energy to generate biuld points\n"
           + "It can store and consume up to 5 energy");

    private final int cost;
    private final int maxEnergy;

    private final String displayName;
    private final String description;

    CellContentType(int cost, int maxEnergy, String displayName, String description) {
        this.cost = cost;
        this.displayName = displayName;
        this.maxEnergy = maxEnergy;
        this.description = description;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }
}
