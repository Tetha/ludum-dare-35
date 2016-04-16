package org.subquark.growing_blob;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EnergyBlip extends Actor {
    private final ShapeRenderer shapeRenderer;

    public EnergyBlip() {
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.translate(getX(), getY(), 0);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0, 0, 0, parentAlpha);
        shapeRenderer.triangle(0, 0, getWidth(), 0, getWidth()/2, getHeight());

        shapeRenderer.setColor(1, 0, 0, parentAlpha);
        shapeRenderer.triangle(1, 1, getWidth()-2, 2, getWidth()/2, getHeight() - 2);

        shapeRenderer.end();

        batch.begin();
    }
}
