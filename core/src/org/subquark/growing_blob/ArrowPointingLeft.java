package org.subquark.growing_blob;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ArrowPointingLeft extends Actor {
    private final ShapeRenderer shapeRenderer;
    public ArrowPointingLeft() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.translate(getX(), getY(), 0);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(getColor());

        float headEndsAt = 2 * getWidth() / 3;

        shapeRenderer.triangle(0, getHeight()/2, headEndsAt, getHeight(), headEndsAt, 0);
        shapeRenderer.rect(getWidth()/2, 2 * getHeight()/6, getWidth(), 2 * getHeight()/6);

        shapeRenderer.end();

        batch.begin();
    }
}
