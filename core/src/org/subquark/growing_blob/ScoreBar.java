package org.subquark.growing_blob;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.function.Supplier;

public class ScoreBar extends Actor {
    private final ShapeRenderer shapeRenderer;
    private final int minValue;
    private final int maxValue;
    private final Supplier<Integer> scoreGetter;

    public ScoreBar(int minValue, int maxValue, Supplier<Integer> scoreGetter) {
        shapeRenderer = new ShapeRenderer();
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.scoreGetter = scoreGetter;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.translate(getX(), getY(), 0);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(getColor());

        int currentScore = scoreGetter.get();
        int scoreInMyRange = currentScore - minValue;
        float rangePercentage = scoreInMyRange / ((float)maxValue - minValue);

        if (rangePercentage < 0) rangePercentage = 0;
        if (rangePercentage > 1) rangePercentage = 1;

        float totalWidth = getWidth();
        float barWidth = totalWidth * rangePercentage;

        shapeRenderer.rect(0, 0, barWidth, getHeight());
        shapeRenderer.end();

        batch.begin();
    }
}
