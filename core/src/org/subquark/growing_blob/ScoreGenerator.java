package org.subquark.growing_blob;

import java.util.Random;

public class ScoreGenerator extends CellContent {
    protected ScoreGenerator(Random r) {
        super(CellContentType.SCORE_GENERATOR);
    }

    @Override
    public void tick() {
        if (getEnergy() == 0) {
            return;
        }

        getCell().getWorld().addPlayerScore(this.getEnergy());
        this.setEnergy(0);
    }
}
