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

        this.addActor(new BlipMeter(3, 3, this::getEnergyInCell));
    }

    private int getEnergyInCell() {
        if (cell.getContent() == null) {
            return 0;
        } else {
            return cell.getContent().getEnergy();
        }
    }
}
