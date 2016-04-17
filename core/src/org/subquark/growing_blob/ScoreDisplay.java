package org.subquark.growing_blob;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.function.Supplier;

public class ScoreDisplay extends Group {
    private Supplier<Integer> scoreGetter;

    private Label heading;
    private Label points;
    private final ScoreBar firstBar;
    private final ScoreBar secondBar;
    private final ScoreBar thirdBar;

    public ScoreDisplay(Skin uiSkin, Supplier<Integer> scoreGetter, int maxForFirstGroup, int maxForSecondGroup, int maxForThirdGroup) {
        this.scoreGetter = scoreGetter;

        heading = new Label("Score", uiSkin);
        heading.setX(0);
        heading.setY(30);

        points = new Label("42", uiSkin);
        points.setX(50);
        points.setY(30);

        firstBar = new ScoreBar(0, maxForFirstGroup,scoreGetter);
        firstBar.setWidth(getWidth());
        firstBar.setHeight(10);
        firstBar.setX(0);
        firstBar.setY(20);
        firstBar.setColor(1f, 1f, 0, 0);
        addActor(firstBar);

        secondBar = new ScoreBar(maxForFirstGroup, maxForSecondGroup,scoreGetter);
        secondBar.setWidth(getWidth());
        secondBar.setHeight(10);
        secondBar.setX(0);
        secondBar.setY(10);
        secondBar.setColor(1f, 0.6f, 0, 0);
        addActor(secondBar);

        thirdBar = new ScoreBar(maxForSecondGroup, maxForThirdGroup,scoreGetter);
        thirdBar.setWidth(getWidth());
        thirdBar.setHeight(10);
        thirdBar.setX(0);
        thirdBar.setY(0);
        thirdBar.setColor(1f, 0.2f, 0, 0);
        addActor(thirdBar);

        addActor(heading);
        addActor(points);
    }

    @Override
    public void act(float timeDelta) {
        points.setText(scoreGetter.get().toString());

        firstBar.setWidth(getWidth());
        secondBar.setWidth(getWidth());
        thirdBar.setWidth(getWidth());
    }
}
