package org.subquark.growing_blob;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class EmitterDisplayFacingDown extends AbstractEmitterDisplay {
    private final ShapeRenderer shapeRenderer;
    private final Emitter emitter;

    private static final int MARGIN = 5;

    public EmitterDisplayFacingDown(Emitter displayedEmitter) {
        this.shapeRenderer = new ShapeRenderer();
        this.emitter = displayedEmitter;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!emitter.isSetup()) return;

        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.translate(getX(), getY(), 0);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(emitter.getColor().getR(), emitter.getColor().getG(), emitter.getColor().getB(), parentAlpha);

        float totalEmitterHeight = getHeight() - MARGIN;
        float totalEmitterWidth = getWidth() - MARGIN;

        // draw the base of the emitter
        shapeRenderer.rect(MARGIN, getHeight() - totalEmitterHeight / 3, totalEmitterWidth, totalEmitterHeight / 3);

        // draw the three beam things
        if(emitter.getLevel() >= 1) shapeRenderer.rect(    MARGIN,                             MARGIN, totalEmitterWidth / 3 - 1, totalEmitterHeight);
        if(emitter.getLevel() >= 2) shapeRenderer.rect(1 + MARGIN +     totalEmitterWidth / 3, MARGIN, totalEmitterWidth / 3 - 2, totalEmitterHeight);
        if(emitter.getLevel() >= 3) shapeRenderer.rect(1 + MARGIN + 2 * totalEmitterWidth / 3, MARGIN, totalEmitterWidth / 3 - 1, totalEmitterHeight);

        shapeRenderer.end();

        batch.begin();
    }

    public void onBulletFired(int rowsTravelled, int columnsTravelled) {
        float totalEmitterWidth = getWidth() - MARGIN;
        float barrelWidth = (getHeight() - MARGIN) / 3;

        float fireY = getY() - 5;

        if (emitter.getLevel() >= 1) {
            float startOfFirstBarrel = MARGIN;
            float centerOfFirstBarrel = startOfFirstBarrel + barrelWidth / 2;

            Vector2 localBulletCoordinates = new Vector2(centerOfFirstBarrel, fireY);
            Vector2 parentCoords = localToParentCoordinates(localBulletCoordinates);
            Vector2 targetCoords = localToParentCoordinates(new Vector2(centerOfFirstBarrel, -50*rowsTravelled + 45));

            BulletDisplay newBullet = BulletDisplay.centeredAt(parentCoords.x, parentCoords.y, emitter.getColor());
            getParent().addActor(newBullet);
            newBullet.addAction(createBulletAnimation(rowsTravelled, columnsTravelled, targetCoords));
        }

        if (emitter.getLevel() >= 2) {
            float startOfSecondBarrel = 1 + MARGIN + totalEmitterWidth / 3;
            float centerOfSecondBarrel = startOfSecondBarrel + barrelWidth / 2;

            Vector2 localBulletCoordinates = new Vector2(centerOfSecondBarrel, fireY);
            Vector2 parentCoords = localToParentCoordinates(localBulletCoordinates);
            Vector2 targetCoords = localToParentCoordinates(new Vector2(centerOfSecondBarrel, -50*rowsTravelled + 45));

            BulletDisplay newBullet = BulletDisplay.centeredAt(parentCoords.x, parentCoords.y, emitter.getColor());
            getParent().addActor(newBullet);
            newBullet.addAction(createBulletAnimation(rowsTravelled, columnsTravelled, targetCoords));
        }

        if (emitter.getLevel() >= 3) {
            float startOfThirdBarrel = 1 + MARGIN + 2 * totalEmitterWidth / 3;
            float centerOfThirdBarrel = startOfThirdBarrel + barrelWidth / 2;

            Vector2 localBulletCoordinates = new Vector2(centerOfThirdBarrel, fireY);
            Vector2 parentCoords = localToParentCoordinates(localBulletCoordinates);

            Vector2 targetCoords = localToParentCoordinates(new Vector2(centerOfThirdBarrel, -50*rowsTravelled + 45));

            BulletDisplay newBullet = BulletDisplay.centeredAt(parentCoords.x, parentCoords.y, emitter.getColor());
            getParent().addActor(newBullet);
            newBullet.addAction(createBulletAnimation(rowsTravelled, columnsTravelled, targetCoords));
        }
    }

    private SequenceAction createBulletAnimation(int rowsTravelled, int columnsTravelled, Vector2 targetCoords) {
        SequenceAction animation = Actions.sequence();
        animation.addAction(moveTo(targetCoords.x, targetCoords.y, travelTime(rowsTravelled)));

        if (rowsTravelled >= 6 || columnsTravelled >= 6) {
            // the bullet fizzeled so we need to fade it out
            animation.addAction(parallel(Actions.moveBy(0, -50, travelTime(1)),
                                         Actions.sizeBy(-2, -2, travelTime(1)),
                                         Actions.fadeOut(travelTime(1))));
        } else {
            // the bullet impacted, it needs to explode or something
            animation.addAction(Actions.sizeBy(2, 2, 0.2f));
        }
        animation.addAction(removeActor());
        return animation;
    }
}
