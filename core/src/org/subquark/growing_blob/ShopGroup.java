package org.subquark.growing_blob;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ShopGroup extends Group {
    private Table table;

    private Label cost;
    private Label description;
    private SelectBox<String> items;

    public ShopGroup(Skin uiSkin) {
        table = new Table();
        table.setFillParent(true);
        addActor(table);

        items = new SelectBox<>(uiSkin);

        String[] descriptions = new String[CellContentType.values().length];
        for (int i = 0; i < descriptions.length; i++) {
            descriptions[i] = CellContentType.values()[i].getDisplayName();
        }
        items.setItems(descriptions);
        table.add(items).expand().left();

        table.row();
        cost = new Label("Cost: ?", uiSkin);
        table.add(cost).expand().left();

        table.row();
        description = new Label("", uiSkin);
        table.add(description).expand().left().top();
    }

    @Override
    public void act (float delta) {
        super.act(delta);

        int selectedTypeIndex = items.getSelectedIndex();
        CellContentType selectedType = CellContentType.values()[selectedTypeIndex];

        cost.setText("Cost: " + selectedType.getCost());
        description.setText(selectedType.getDescription());
    }
}
