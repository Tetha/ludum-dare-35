package org.subquark.growing_blob;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

public class CellDisplay extends Group {
    private Cell cell;
    private CellBackgroundDisplay background;
    private EnergyBlip[][] rcBlips;

    public CellDisplay(Cell displayedCell) {
        this.cell = displayedCell;

        background = new CellBackgroundDisplay(cell);
        background.setWidth(50);
        background.setHeight(50);
        addActor(background);

        Group blipGroup = new Group();
        blipGroup.setX(10);
        blipGroup.setY(10);

        this.addActor(blipGroup);
        rcBlips = new EnergyBlip[4][3];
        for (int blipRow = 0; blipRow < 3; blipRow++) {
            for(int blipCol = 0; blipCol < 3; blipCol++) {
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
        for (int blipRow = 0; blipRow < 3; blipRow++) {
            for(int blipCol = 0; blipCol < 3; blipCol++) {
                if (cell.getContent() == null) {
                    rcBlips[blipRow][blipCol].setVisible(false);
                } else if (drawnEnergy < cell.getContent().getEnergy()) {
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
