package org.subquark.growing_blob;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlusSign extends Actor {
    private final ShapeRenderer shapeRenderer;

    public PlusSign() {
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
        shapeRenderer.rect(0, getHeight()/3, getWidth(), getHeight()/3);
        shapeRenderer.rect(getWidth()/3, 0, getWidth()/3, getHeight());

        shapeRenderer.setColor(getColor().r, getColor().g, getColor().b, parentAlpha);
        shapeRenderer.rect(0 + 1, getHeight()/3 + 1, getWidth() - 2, getHeight()/3 - 2);
        shapeRenderer.rect(getWidth()/3 + 1, 0 + 1, getWidth()/3 - 2, getHeight() - 2);

        shapeRenderer.end();

        batch.begin();
    }
}
