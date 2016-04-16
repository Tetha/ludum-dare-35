package org.subquark.growing_blob;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EmitterDisplayFacingUp extends Actor {
    private final ShapeRenderer shapeRenderer;
    private final Emitter emitter;

    private static final int MARGIN = 5;

    public EmitterDisplayFacingUp(Emitter displayedEmitter) {
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
        shapeRenderer.rect(MARGIN, MARGIN, totalEmitterWidth, totalEmitterHeight / 3);

        // draw the three beam things
        if(emitter.getLevel() >= 1) shapeRenderer.rect(    MARGIN,                             MARGIN, totalEmitterWidth / 3 - 1, totalEmitterHeight);
        if(emitter.getLevel() >= 2) shapeRenderer.rect(1 + MARGIN +     totalEmitterWidth / 3, MARGIN, totalEmitterWidth / 3 - 2, totalEmitterHeight);
        if(emitter.getLevel() >= 3) shapeRenderer.rect(1 + MARGIN + 2 * totalEmitterWidth / 3, MARGIN, totalEmitterWidth / 3 - 1, totalEmitterHeight);

        shapeRenderer.end();

        batch.begin();
    }

    public void onFire() {
    }
}
