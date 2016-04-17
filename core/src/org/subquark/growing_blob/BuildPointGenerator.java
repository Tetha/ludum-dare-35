package org.subquark.growing_blob;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BuildPointGenerator extends CellContent {
    protected BuildPointGenerator(Random r) {
        super(CellContentType.BUILD_POINT_GENERATOR);
    }

    @Override
    public void tick() {
        if (getEnergy() == 0) {
            return;
        }

        getCell().getWorld().addPlayerBuildPoints(this.getEnergy());
        this.setEnergy(0);
    }
}
