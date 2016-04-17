package org.subquark.growing_blob;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ArrowPointingRight extends Actor {
    private final ShapeRenderer shapeRenderer;
    public ArrowPointingRight() {
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

        float headStartsAt = getWidth() / 3;

        shapeRenderer.triangle(headStartsAt, 0, headStartsAt, getHeight(), getWidth(), getHeight()/2);
        shapeRenderer.rect(0, 2 * getHeight()/6, getWidth()/2, 2 * getHeight()/6);

        shapeRenderer.end();

        batch.begin();
    }
}
