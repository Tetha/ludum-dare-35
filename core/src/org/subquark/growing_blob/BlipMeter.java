package org.subquark.growing_blob;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

import javax.xml.ws.Provider;
import java.util.function.Supplier;

public class BlipMeter extends Group {
    private final Supplier<Integer> currentEnergyProvider;
    private final int blipRows;
    private final int blipCols;

    private final EnergyBlip[][] rcBlips;

    public BlipMeter(int blipRows, int blipCols, Supplier<Integer> energyProvider) {
        this.currentEnergyProvider = energyProvider;
        this.blipRows = blipRows;
        this.blipCols = blipCols;

        Group blipGroup = new Group();
        blipGroup.setX(10);
        blipGroup.setY(10);

        this.addActor(blipGroup);
        rcBlips = new EnergyBlip[blipRows][blipCols];
        for (int blipRow = 0; blipRow < blipRows; blipRow++) {
            for(int blipCol = 0; blipCol < blipCols; blipCol++) {
                rcBlips[blipRow][blipCol] = new EnergyBlip();
                rcBlips[blipRow][blipCol].setVisible(false);
                rcBlips[blipRow][blipCol].setWidth(10);
                rcBlips[blipRow][blipCol].setHeight(10);
                rcBlips[blipRow][blipCol].setX(blipCol * 10);
                rcBlips[blipRow][blipCol].setY(blipRow * 10);

                blipGroup.addActor(rcBlips[blipRow][blipCol]);
            }
        }
    }

    @Override
    public void draw(Batch batch, float delta) {
        int drawnEnergy = 0;
        int energyToDraw = currentEnergyProvider.get();
        for (int blipRow = 0; blipRow < blipRows; blipRow++) {
            for(int blipCol = 0; blipCol < blipCols; blipCol++) {
                if (drawnEnergy < energyToDraw) {
                    rcBlips[blipRow][blipCol].setVisible(true);
                    drawnEnergy++;
                } else {
                    rcBlips[blipRow][blipCol].setVisible(false);
                }
            }
        }

        super.draw(batch, delta);
    }
}
