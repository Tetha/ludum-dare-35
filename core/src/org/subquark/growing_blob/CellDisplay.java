package org.subquark.growing_blob;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Random;
import java.util.function.Consumer;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;

public class CellDisplay extends Group {
    private final Random r;
    private Cell cell;
    private CellBackgroundDisplay background;

    private static final int TRANSFER_DISTANCE = 20;

    private Consumer<Cell> cellClickObserver;

    public CellDisplay(Cell displayedCell) {
        r = new Random();
        this.cell = displayedCell;

        this.cell.onEnergyTransferredTowardsRight(this::onEnergyTransferTowardsRight);
        this.cell.onEnergyTransferredTowardsLeft(this::onEnergyTransferTowardsLeft);
        this.cell.onEnergyTransferredTowardsDown(this::onEnergyTransferTowardsDown);
        this.cell.onEnergyTransferredTowardsUp(this::onEnergyTransferTowardsUp);
        this.cell.setScoreGeneratedObserver(this::onScoreProduced);
        this.cell.setBuildPointGainedObserver(this::onBuildPointProduced);

        background = new CellBackgroundDisplay(cell);
        background.setWidth(50);
        background.setHeight(50);
        addActor(background);

        BlipMeter meter = new BlipMeter(3, 3, this::getEnergyInCell);
        meter.setX(10);
        meter.setY(10);
        this.addActor(meter);

        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                cellClickObserver.accept(cell);
            }
        });
    }

    public void onEnergyTransferTowardsRight() {
        BulletDisplay energyBullet = BulletDisplay.centeredAt(
                getWidth() - 10,
                getHeight() / 2 + r.nextFloat() * getHeight() / 2 - getHeight() / 4, EmitterColor.YELLOW
        );

        energyBullet.setWidth(4);
        energyBullet.setHeight(4);
        energyBullet.addAction(sequence(
                Actions.moveBy(TRANSFER_DISTANCE, r.nextInt(10) - 5, 0.4f),
                Actions.removeActor()
        ));
        energyBullet.setZIndex(0);
        addActor(energyBullet);
    }
    public void onEnergyTransferTowardsLeft() {
        BulletDisplay energyBullet = BulletDisplay.centeredAt(
                10,
                getHeight() / 2 + r.nextFloat() * getHeight() / 2 - getHeight() / 4, EmitterColor.YELLOW
        );

        energyBullet.setWidth(4);
        energyBullet.setHeight(4);
        energyBullet.addAction(sequence(
                Actions.moveBy(-TRANSFER_DISTANCE, r.nextInt(10) - 5, 0.4f),
                Actions.removeActor()
        ));
        energyBullet.setZIndex(0);
        addActor(energyBullet);
    }

    public void onEnergyTransferTowardsDown() {
        BulletDisplay energyBullet = BulletDisplay.centeredAt(
                getWidth() / 2 + r.nextFloat() * getWidth() / 2 - getWidth() / 4,
                10,
                EmitterColor.YELLOW
        );

        energyBullet.setWidth(4);
        energyBullet.setHeight(4);
        energyBullet.addAction(sequence(
                Actions.moveBy(r.nextInt(10) - 5, -TRANSFER_DISTANCE, 0.4f),
                Actions.removeActor()
        ));
        energyBullet.setZIndex(0);
        addActor(energyBullet);
    }
    public void onEnergyTransferTowardsUp() {
        BulletDisplay energyBullet = BulletDisplay.centeredAt(
                getWidth() / 2 + r.nextFloat() * getWidth() / 2 - getWidth() / 4,
                getHeight() - 10,
                EmitterColor.YELLOW
        );

        energyBullet.setWidth(4);
        energyBullet.setHeight(4);
        energyBullet.addAction(sequence(
                Actions.moveBy(r.nextInt(10) - 5, TRANSFER_DISTANCE, 0.4f),
                Actions.removeActor()
        ));
        energyBullet.setZIndex(0);
        addActor(energyBullet);
    }

    public void onScoreProduced() {
        PlusSign particle = new PlusSign();

        float startX = r.nextFloat()*getWidth()*0.5f;
        float startY = r.nextFloat()*getHeight()*0.5f;

        float height = r.nextFloat() * 40;

        particle.setColor(cell.getContent().getType().getBackgroundColor());
        particle.setX(getWidth()*0.25f + startX);
        particle.setY(getWidth()*0.25f + startY);
        particle.setWidth(10);
        particle.setHeight(10);

        this.addActor(particle);

        particle.addAction(sequence(
            Actions.moveBy(0, height, 0.5f),
                Actions.removeActor()
        ));
    }

    public void onBuildPointProduced() {
        PlusSign particle = new PlusSign();

        float startX = r.nextFloat()*getWidth()*0.5f;
        float startY = r.nextFloat()*getHeight()*0.5f;

        float height = r.nextFloat() * 40;

        particle.setColor(cell.getContent().getType().getBackgroundColor());
        particle.setX(getWidth()*0.25f + startX);
        particle.setY(getWidth()*0.25f + startY);
        particle.setWidth(10);
        particle.setHeight(10);

        this.addActor(particle);

        particle.addAction(sequence(
                Actions.moveBy(0, height, 0.3f),
                Actions.removeActor()
        ));
    }

    private int getEnergyInCell() {
        if (cell.getContent() == null) {
            return 0;
        } else {
            return cell.getContent().getEnergyForDisplay();
        }
    }

    public void setCellClickObserver(Consumer<Cell> cellClickObserver) {
        this.cellClickObserver = cellClickObserver;
    }
}
