package org.subquark.growing_blob;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class EmitterDisplayFacingDown extends Actor {
    private final ShapeRenderer shapeRenderer;
    private final Emitter emitter;

    private static final int MARGIN = 5;

    public EmitterDisplayFacingDown(Emitter displayedEmitter) {
        this.shapeRenderer = new ShapeRenderer();
        this.emitter = displayedEmitter;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
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

    public void onFired() {
        float totalEmitterWidth = getWidth() - MARGIN;
        float barrelWidth = (getHeight() - MARGIN) / 3;

        float fireY = getY() - 5;

        if (emitter.getLevel() >= 1) {
            float startOfFirstBarrel = MARGIN;
            float centerOfFirstBarrel = startOfFirstBarrel + barrelWidth / 2;

            Vector2 localBulletCoordinates = new Vector2(centerOfFirstBarrel, fireY);
            Vector2 parentCoords = localToParentCoordinates(localBulletCoordinates);
            Vector2 targetCoords = localToParentCoordinates(new Vector2(centerOfFirstBarrel, -300));

            BulletDisplay newBullet = BulletDisplay.centeredAt(parentCoords.x, parentCoords.y, emitter.getColor());
            getParent().addActor(newBullet);
            newBullet.addAction(sequence(moveTo(targetCoords.x, targetCoords.y, 2),
                                         removeActor()));
        }

        if (emitter.getLevel() >= 2) {
            float startOfSecondBarrel = 1 + MARGIN +     totalEmitterWidth / 3;
            float centerOfSecondBarrel = startOfSecondBarrel + barrelWidth / 2;

            Vector2 localBulletCoordinates = new Vector2(centerOfSecondBarrel, fireY);
            Vector2 parentCoords = localToParentCoordinates(localBulletCoordinates);
            Vector2 targetCoords = localToParentCoordinates(new Vector2(centerOfSecondBarrel, -300));

            BulletDisplay newBullet = BulletDisplay.centeredAt(parentCoords.x, parentCoords.y, emitter.getColor());
            getParent().addActor(newBullet);
            newBullet.addAction(sequence(moveTo(targetCoords.x, targetCoords.y, 2),
                                          removeActor()));
        }

        if (emitter.getLevel() >= 3) {
            float startOfThirdBarrel = 1 + MARGIN + 2 * totalEmitterWidth / 3;
            float centerOfThirdBarrel = startOfThirdBarrel + barrelWidth / 2;

            Vector2 localBulletCoordinates = new Vector2(centerOfThirdBarrel, fireY);
            Vector2 parentCoords = localToParentCoordinates(localBulletCoordinates);

            Vector2 targetCoords = localToParentCoordinates(new Vector2(centerOfThirdBarrel, -300));

            BulletDisplay newBullet = BulletDisplay.centeredAt(parentCoords.x, parentCoords.y, emitter.getColor());
            getParent().addActor(newBullet);
            newBullet.addAction(sequence(moveTo(targetCoords.x, targetCoords.y, 2),
                                         removeActor()));
        }
    }
}
