package org.subquark.growing_blob;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class EmitterDisplayFacingRight extends Actor {
    private final ShapeRenderer shapeRenderer;
    private final Emitter emitter;

    private static final int MARGIN = 5;

    public EmitterDisplayFacingRight(Emitter displayedEmitter) {
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
        shapeRenderer.rect(MARGIN, MARGIN, totalEmitterWidth / 3, totalEmitterHeight);

        // draw the three beam things
        if(emitter.getLevel() >= 1) shapeRenderer.rect(MARGIN,     MARGIN,                              totalEmitterWidth, totalEmitterHeight / 3 - 1);
        if(emitter.getLevel() >= 2) shapeRenderer.rect(MARGIN, 1 + MARGIN +     totalEmitterHeight / 3, totalEmitterWidth, totalEmitterHeight / 3 - 2);
        if(emitter.getLevel() >= 3) shapeRenderer.rect(MARGIN, 1 + MARGIN + 2 * totalEmitterHeight / 3, totalEmitterWidth, totalEmitterHeight / 3 - 1);

        shapeRenderer.end();

        batch.begin();
    }

    public void onBulletFired(int rowsTravelled, int columnsTravelled) {
        float totalEmitterWidth = getWidth() - MARGIN;
        float barrelWidth = (getHeight() - MARGIN) / 3;

        float fireX = getX() + getWidth() + 5;

        if (emitter.getLevel() >= 1) {
            float startOfFirstBarrel = MARGIN;
            float centerOfFirstBarrel = startOfFirstBarrel + barrelWidth / 2;

            Vector2 localBulletCoordinates = new Vector2(fireX, centerOfFirstBarrel);
            Vector2 parentCoords = localToParentCoordinates(localBulletCoordinates);
            Vector2 targetCoords = localToParentCoordinates(new Vector2(50 * rowsTravelled, centerOfFirstBarrel));

            System.out.println(parentCoords);
            System.out.println(targetCoords);
            BulletDisplay newBullet = BulletDisplay.centeredAt(parentCoords.x, parentCoords.y, emitter.getColor());
            getParent().addActor(newBullet);
            newBullet.addAction(sequence(moveTo(targetCoords.x, targetCoords.y, 2),
                    removeActor()));
        }

        if (emitter.getLevel() >= 2) {
            float startOfSecondBarrel = 1 + MARGIN +     totalEmitterWidth / 3;
            float centerOfSecondBarrel = startOfSecondBarrel + barrelWidth / 2;

            Vector2 localBulletCoordinates = new Vector2(fireX, centerOfSecondBarrel);
            Vector2 parentCoords = localToParentCoordinates(localBulletCoordinates);
            Vector2 targetCoords = localToParentCoordinates(new Vector2(50 * rowsTravelled, centerOfSecondBarrel));

            BulletDisplay newBullet = BulletDisplay.centeredAt(parentCoords.x, parentCoords.y, emitter.getColor());
            getParent().addActor(newBullet);
            newBullet.addAction(sequence(moveTo(targetCoords.x, targetCoords.y, 2),
                    removeActor()));
        }

        if (emitter.getLevel() >= 3) {
            float startOfThirdBarrel = 1 + MARGIN + 2 * totalEmitterWidth / 3;
            float centerOfThirdBarrel = startOfThirdBarrel + barrelWidth / 2;

            Vector2 localBulletCoordinates = new Vector2(fireX, centerOfThirdBarrel);
            Vector2 parentCoords = localToParentCoordinates(localBulletCoordinates);

            Vector2 targetCoords = localToParentCoordinates(new Vector2(50 * rowsTravelled, centerOfThirdBarrel));

            BulletDisplay newBullet = BulletDisplay.centeredAt(parentCoords.x, parentCoords.y, emitter.getColor());
            getParent().addActor(newBullet);
            newBullet.addAction(sequence(moveTo(targetCoords.x, targetCoords.y, 2),
                    removeActor()));
        }
    }
}
