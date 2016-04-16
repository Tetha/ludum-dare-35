package org.subquark.growing_blob;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnergyRouter extends CellContent {
    private final Random r;
    protected EnergyRouter(Random r) {
        super(CellContentType.ROUTER);
        this.r = r;
    }

    @Override
    public void tick() {
        if (getEnergy() == 0) {
            return;
        }

        Cell myCell = getCell();
        int row = myCell.getRow();
        int col = myCell.getCol();

        Cell northNeighbour = myCell.getWorld().getCell(row + 1, col);
        Cell southNeighbour = myCell.getWorld().getCell(row - 1, col);
        Cell eastNeighbour = myCell.getWorld().getCell(row, col + 1);
        Cell westNeighbour = myCell.getWorld().getCell(row, col - 1);

        List<CellContent> acceptingNeighbours = new ArrayList<>();
        if (isEnergyAcceptingNeighbour(northNeighbour)) acceptingNeighbours.add(northNeighbour.getContent());
        if (isEnergyAcceptingNeighbour(southNeighbour)) acceptingNeighbours.add(southNeighbour.getContent());
        if (isEnergyAcceptingNeighbour(eastNeighbour)) acceptingNeighbours.add(eastNeighbour.getContent());
        if (isEnergyAcceptingNeighbour(westNeighbour)) acceptingNeighbours.add(westNeighbour.getContent());

        int transferCapacity = 5;
        while (getEnergy() > 0 && transferCapacity > 0 && acceptingNeighbours.size() > 0 ) {
            int idx = r.nextInt(acceptingNeighbours.size());
            CellContent target = acceptingNeighbours.get(idx);
            target.addEnergy(1);
            this.removeEnergy(1);
            transferCapacity --;
            if (!target.canAcceptMoreEnergy()) {
                acceptingNeighbours.remove(idx);
            }
        }
        System.out.println("Capacity left: " + transferCapacity);

    }

    private boolean isEnergyAcceptingNeighbour(Cell cell) {
        return cell != null && cell.getContent() != null && cell.getContent().canAcceptMoreEnergy();
    }
}
