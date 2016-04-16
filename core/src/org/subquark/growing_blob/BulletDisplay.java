package org.subquark.growing_blob;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BulletDisplay extends Actor {
    private final ShapeRenderer shapeRenderer;
    private final Color color;

    private static final int RADIUS = 5;

    public BulletDisplay(Color color) {
        this.shapeRenderer = new ShapeRenderer();
        this.color = color;
    }

    public static BulletDisplay centeredAt(float x, float y, Color color) {
        BulletDisplay result = new BulletDisplay(color);
        result.setWidth(RADIUS * 2);
        result.setHeight(RADIUS * 2);
        result.setX(x - RADIUS);
        result.setY(y - RADIUS);
        return result;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.translate(getX(), getY(), 0);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0, 0, 0, parentAlpha);
        shapeRenderer.circle(RADIUS, RADIUS, RADIUS);

        shapeRenderer.setColor(color.getR(), color.getG(), color.getB(), parentAlpha);
        shapeRenderer.circle(RADIUS, RADIUS, RADIUS - 2);

        shapeRenderer.end();

        batch.begin();
    }
}
