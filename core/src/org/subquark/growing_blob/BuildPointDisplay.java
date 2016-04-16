package org.subquark.growing_blob;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.function.Supplier;

public class BuildPointDisplay extends Group {
    private BlipMeter energyBlips;
    private Label heading;
    private Label points;
    private Supplier<Integer> buildPointSupplier;

    public BuildPointDisplay(Skin uiSkin, Supplier<Integer> buildpointSupplier) {
        this.buildPointSupplier = buildpointSupplier;

        energyBlips = new BlipMeter(3, 10, buildpointSupplier);
        energyBlips.setX(30);
        energyBlips.setY(0);

        heading = new Label("Buildpoints", uiSkin);
        heading.setX(0);
        heading.setY(25);

        points = new Label("42", uiSkin);
        points.setX(0);
        points.setY(0);

        addActor(energyBlips);
        addActor(heading);
        addActor(points);
    }

    @Override
    public void draw(Batch batch, float deltaTime) {
        points.setText(buildPointSupplier.get().toString());
        super.draw(batch, deltaTime);
    }
}
