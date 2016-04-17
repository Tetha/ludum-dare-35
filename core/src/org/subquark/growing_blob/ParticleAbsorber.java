package org.subquark.growing_blob;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticleAbsorber extends CellContent {
    private final Random r;

    public ParticleAbsorber(Random r) {
        super(CellContentType.PARTICLE_ABSORBER);
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

            if (target.getCell() == northNeighbour) myCell.notifyEnergyTransferredTowardsUp();
            if (target.getCell() == eastNeighbour) myCell.notifyEnergyTransferredTowardsRight();
            if (target.getCell() == southNeighbour) myCell.notifyEnergyTransferredTowardsDown();
            if (target.getCell() == westNeighbour) myCell.notifyEnergyTransferredTowardsLeft();

            target.addEnergy(1);
            this.removeEnergy(1);
            transferCapacity --;
            if (!target.canAcceptMoreEnergy()) {
                acceptingNeighbours.remove(idx);
            }
        }
    }

    private boolean isEnergyAcceptingNeighbour(Cell cell) {
        return cell != null && cell.getContent() != null && cell.getContent().canAcceptMoreEnergy();
    }
}
