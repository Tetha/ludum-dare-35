package org.subquark.growing_blob;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EmitterDisplayFacingLeft extends Actor {
    private final ShapeRenderer shapeRenderer;
    private final Emitter emitter;

    private static final int MARGIN = 5;

    public EmitterDisplayFacingLeft(Emitter displayedEmitter) {
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
        shapeRenderer.rect(getWidth() - totalEmitterWidth/3, MARGIN,
                           totalEmitterWidth / 3, totalEmitterHeight);

        // draw the three beam things
        if(emitter.getLevel() >= 1) shapeRenderer.rect(MARGIN,     MARGIN,                              totalEmitterWidth, totalEmitterHeight / 3 - 1);
        if(emitter.getLevel() >= 2) shapeRenderer.rect(MARGIN, 1 + MARGIN +     totalEmitterHeight / 3, totalEmitterWidth, totalEmitterHeight / 3 - 2);
        if(emitter.getLevel() >= 3) shapeRenderer.rect(MARGIN, 1 + MARGIN + 2 * totalEmitterHeight / 3, totalEmitterWidth, totalEmitterHeight / 3 - 1);

        shapeRenderer.end();

        batch.begin();
    }
}
