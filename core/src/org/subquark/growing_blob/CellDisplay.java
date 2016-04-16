package org.subquark.growing_blob;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CellDisplay extends Actor {
    private final ShapeRenderer shapeRenderer;
    private final Cell cell;

    private static final int MARGIN = 5;

    public CellDisplay(Cell cell) {
        this.cell = cell;
        this.shapeRenderer = new ShapeRenderer();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.translate(getX(), getY(), 0);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.5f, 0.5f, 0.5f, parentAlpha);
        shapeRenderer.rect(MARGIN, MARGIN, getWidth() - MARGIN, getHeight() - MARGIN);
        shapeRenderer.end();

        if (!cell.isEmpty()) {
            drawContents(parentAlpha);
        }
        batch.begin();
    }

    private void drawContents(float parentAlpha) {
        if (cell.getContent().getType() == CellContentType.PARTICLE_ABSORBER) {
            drawParticleAbsorber(parentAlpha);
        }
    }

    private void drawParticleAbsorber(float parentAlpha) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.8f, 0.2f, 0.2f, parentAlpha);
        shapeRenderer.rect(10, 10, getWidth() - 2*10, getHeight() - 2*10);
        shapeRenderer.end();
    }
}
