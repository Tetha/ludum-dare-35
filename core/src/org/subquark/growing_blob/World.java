package org.subquark.growing_blob;

import com.badlogic.gdx.utils.Timer;
import com.sun.imageio.spi.InputStreamImageInputStreamSpi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class World {
    private List<Emitter> leftEmitters;
    private List<Emitter> rightEmitters;
    private List<Emitter> topEmitters;
    private List<Emitter> bottomEmitters;

    private List<List<Cell>> rcCells;

    private int callbacksSubmitted;

    private int playerBuildPoints = 0;

    private int turnsSimulated = 0;
    private boolean isSimulating;

    private Random r;

    private static final int TURNS_PER_EMITTER = 3;

    public void improveEmitters() {
        int chance = r.nextInt(10);
        if (chance < 2) {
            upgradeExistingEmitter();;
        } else {
            createNewEmitter();
        }
    }

    private void createNewEmitter() {
        List<Emitter> nonSetupEmitters = allEmitterStream().filter(e -> !e.isSetup()).collect(Collectors.toList());
        Emitter createdEmitter = nonSetupEmitters.get(r.nextInt(nonSetupEmitters.size()));
        Color emitterColor = Color.values()[r.nextInt(Color.values().length)];

        createdEmitter.setLevel(1);
        createdEmitter.setIsSetup(true);
        createdEmitter.setColor(emitterColor);
    }

    private void upgradeExistingEmitter() {
        List<Emitter> existingUpgradableEmitters = allEmitterStream().filter(e -> e.isSetup() && e.getLevel() < 3).collect(Collectors.toList());
        Emitter upgradedEmitter = existingUpgradableEmitters.get(r.nextInt(existingUpgradableEmitters.size()));
        upgradedEmitter.increaseLevel();
    }

    private Stream<Emitter> allEmitterStream() {
        return Stream.concat(
            Stream.concat(leftEmitters.stream(), rightEmitters.stream()),
            Stream.concat(topEmitters.stream(), bottomEmitters.stream())
        );
    }

    public void simulateTurn() {
        if (callbacksSubmitted != 0) {
            System.err.println("Something weird is going on - callbacksSubmitted should be 0, but is " + callbacksSubmitted);
            callbacksSubmitted = 0;
        }

        isSimulating = true;

        handleLeftEmitters();
        handleRightEmitters();
        handleTopEmitters();
        handleBottomEmitters();
    }

    public boolean isSimulating() {
        return isSimulating;
    }

    private void finishSimulating() {
        turnsSimulated++;
        isSimulating = false;
    }

    private void handleLeftEmitters() {
        for(Emitter emitter : leftEmitters) {
            if (!emitter.isSetup()) continue;

            int row  = emitter.getRow();
            int collidedCol = -1;

            for ( int column = 0; column < 6; column ++) {
                Cell cell = getCell(row, column);
                if (!cell.isEmpty() ) {
                    collidedCol = column;
                    break;
                }
            }

            if (collidedCol == -1) {
                emitter.fireBullet(0, 6, () -> {});
            } else {
                emitter.fireBullet(0, collidedCol, onCellCollision(emitter.getLevel(), row, collidedCol));
            }
        }
    }

    private Runnable onCellCollision(int energy, int row, int collidedCol) {
        callbacksSubmitted++;
        System.out.println("Callbacks submitted are: " + callbacksSubmitted);
        return () -> {
            callbacksSubmitted--;
            Cell collidedCell = getCell(row, collidedCol);
            if (collidedCell.getContent().getType() == CellContentType.PARTICLE_ABSORBER) {
                collidedCell.getContent().addParticleEnergy(energy);
            }

            System.out.println("Callbaks submitted is: " + callbacksSubmitted);
            if (callbacksSubmitted == 0) {
                Timer.schedule(new Timer.Task() {
                    public void run() {
                        tickCells();
                        commitAllCells();
                        maybeUpgradeEmitters();
                        finishSimulating();
                    }
                }, 2f);
            }
        };
    }

    private void maybeUpgradeEmitters() {
        if (turnsSimulated > 0 && turnsSimulated % TURNS_PER_EMITTER == 0) {
            improveEmitters();
        }
    }
    private void commitAllCells() {
        for (List<Cell> row : rcCells) {
            for (Cell cell : row) {
                if (cell.getContent() != null) {
                    cell.getContent().commitEnergy();
                }
            }
        }
    }

    private void tickCells() {
        for (List<Cell> row : rcCells) {
            for (Cell cell : row) {
                if (cell.getContent() != null) {
                    cell.getContent().tick();
                }
            }
        }
    }

    private void handleRightEmitters() {
        for(Emitter emitter : rightEmitters) {
            if (!emitter.isSetup()) continue;

            int row  = emitter.getRow();
            int collidedCol = -1;

            for ( int column = 5; 0 <= column; column --) {
                Cell cell = getCell(row, column);
                if (!cell.isEmpty() ) {
                    collidedCol = column;
                    break;
                }
            }

            if (collidedCol == -1) {
                emitter.fireBullet(0, 6, () -> {});
            } else {
                emitter.fireBullet(0, 6 - collidedCol, onCellCollision(emitter.getLevel(), row, collidedCol));
            }
        }
    }

    private void handleTopEmitters() {
        for(Emitter emitter : topEmitters) {
            if (!emitter.isSetup()) continue;

            int col  = emitter.getColumn();
            int collidedRow = -1;

            for ( int row = 5; 0 <= row; row --) {
                Cell cell = getCell(row, col);
                if (!cell.isEmpty() ) {
                    collidedRow = row;
                    break;
                }
            }

            if (collidedRow == -1) {
                emitter.fireBullet(6, 0, () -> {});
            } else {
                emitter.fireBullet(6 - collidedRow, 0, onCellCollision(emitter.getLevel(), collidedRow, col));
            }
        }
    }

    private void handleBottomEmitters() {
        for(Emitter emitter : bottomEmitters) {
            if (!emitter.isSetup()) continue;

            int col  = emitter.getColumn();
            int collidedRow = -1;

            for ( int row = 0; row < 6; row ++) {
                Cell cell = getCell(row, col);
                if (!cell.isEmpty() ) {
                    collidedRow = row;
                    break;
                }
            }

            if (collidedRow == -1) {
                emitter.fireBullet(6, 0, () -> {});
            } else {
                emitter.fireBullet(collidedRow, 0, onCellCollision(emitter.getLevel(), collidedRow, col));
            }
        }
    }

    private void bulletAnimationComplete() {
        System.out.println("Kaboom");
    }

    public Cell getCell(int row, int column) {
        if (!(0 <= row && row < rcCells.size())) {
            return null;
        }
        List<Cell> rowList = rcCells.get(row);

        if (!(0 <= column && column < rowList.size())) {
            return null;
        }
        return rowList.get(column);
    }

    public int getPlayerBuildPoints() { return playerBuildPoints; }

    public void addPlayerBuildPoints(int energy) {
        this.playerBuildPoints += energy;
    }

    public void reducePlayerBuildPointsBy(int cost) {
        playerBuildPoints -= cost;
    }

    public List<Emitter> getLeftEmitters() {
        return leftEmitters;
    }

    public void setLeftEmitters(List<Emitter> leftEmitters) {
        this.leftEmitters = leftEmitters;
    }

    public List<Emitter> getRightEmitters() {
        return rightEmitters;
    }

    public void setRightEmitters(List<Emitter> rightEmitters) {
        this.rightEmitters = rightEmitters;
    }

    public List<Emitter> getTopEmitters() {
        return topEmitters;
    }

    public void setTopEmitters(List<Emitter> topEmitters) {
        this.topEmitters = topEmitters;
    }

    public List<Emitter> getBottomEmitters() {
        return bottomEmitters;
    }

    public void setBottomEmitters(List<Emitter> bottomEmitters) {
        this.bottomEmitters = bottomEmitters;
    }

    public List<List<Cell>> getRcCells() {
        return rcCells;
    }

    public void setRcCells(List<List<Cell>> rcCells) {
        this.rcCells = rcCells;
    }

    public Random getR() {
        return r;
    }

    public void setRandom(Random r) {
        this.r = r;
    }
}
