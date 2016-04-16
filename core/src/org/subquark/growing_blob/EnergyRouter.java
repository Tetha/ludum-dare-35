package org.subquark.growing_blob;

public class EnergyRouter extends CellContent {
    protected EnergyRouter() {
        super(CellContentType.ROUTER);
    }

    @Override
    public void tick() {
        System.out.println("Passing energy around");
    }
}
