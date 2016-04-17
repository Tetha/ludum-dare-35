package org.subquark.growing_blob;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CellContentMarker extends Actor {
    private final ShapeRenderer shapeRenderer;
    private CellContentType type;

    public CellContentMarker() {
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.translate(getX(), getY(), 0);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(getType().getBackgroundColor());
        shapeRenderer.rect(0, 0, getWidth(), getHeight());
        shapeRenderer.end();

        batch.begin();
    }

    public CellContentType getType() {
        return type;
    }

    public void setType(CellContentType type) {
        this.type = type;
    }
}
