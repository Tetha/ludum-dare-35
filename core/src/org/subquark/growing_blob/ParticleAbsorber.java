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

        if (acceptingNeighbours.size() > 0 ) {
            CellContent target = acceptingNeighbours.get(r.nextInt(acceptingNeighbours.size()));
            target.addEnergy(1);
            this.removeEnergy(1);
        }
    }

    private boolean isEnergyAcceptingNeighbour(Cell cell) {
        return cell != null && cell.getContent() != null && cell.getContent().canAcceptMoreEnergy();
    }
}
