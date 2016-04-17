package org.subquark.growing_blob;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrowingBlobGame extends ApplicationAdapter {
	private Stage stage;

    private World simulator = new World();

    private List<EmitterDisplayFacingDown> firedEmitters;
    private ShopGroup shop;
    private Random r;

    @Override
	public void create () {
        r = new Random(42);
		stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        createCellDisplays();
        createLeftEmitters(r);
        createRightEmitters(r);
        createTopEmitters(r);
        createBottomEmitters(r);

        Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

        BuildPointDisplay buildPointDisplay = new BuildPointDisplay(uiSkin, simulator::getPlayerBuildPoints);
        stage.addActor(buildPointDisplay);
        buildPointDisplay.setX(400);
        buildPointDisplay.setY(400);
        buildPointDisplay.setHeight(50);
        buildPointDisplay.setWidth(150);
        buildPointDisplay.setDebug(true);

        shop = new ShopGroup(uiSkin);
        stage.addActor(shop);
        shop.setX(400);
        shop.setY(0);
        shop.setHeight(400);
        shop.setWidth(200);
        shop.setDebug(true);

        Emitter testEmitter = simulator.getLeftEmitters().get(3);
        testEmitter.setColor(Color.YELLOW);
        testEmitter.setIsSetup(true);
        testEmitter.setLevel(1);

        Emitter speedTestEmitter = simulator.getRightEmitters().get(4);
        speedTestEmitter.setColor(Color.BLUE);
        speedTestEmitter.setIsSetup(true);
        speedTestEmitter.setLevel(2);

        Emitter noiseEmitter = simulator.getBottomEmitters().get(2);
        noiseEmitter.setColor(Color.RED);
        noiseEmitter.setIsSetup(true);
        noiseEmitter.setLevel(3);

        Cell testCell = simulator.getCell(3, 2);
        testCell.setCellContent(new ParticleAbsorber(r));

        Cell routerCell = simulator.getCell(3, 3);
        routerCell.setCellContent(new EnergyRouter(r));

        routerCell = simulator.getCell(2, 3);
        routerCell.setCellContent(new EnergyRouter(r));

        routerCell = simulator.getCell(1, 3);
        routerCell.setCellContent(new EnergyRouter(r));

        routerCell = simulator.getCell(0, 3);
        routerCell.setCellContent(new EnergyRouter(r));

        Cell builderCell = simulator.getCell(0, 4);
        builderCell.setCellContent(CellContentType.BUILD_POINT_GENERATOR.instantiate(r));
    }

    private void buyStuffFor(Cell c) {
        CellContentType selectedType = shop.getSelectedCellType();
        c.setCellContent(selectedType.instantiate(r));
    }

    private void createBottomEmitters(Random r) {
        Group bottomEmitterDisplays = new Group();
        List<Emitter> bottomEmitters = new ArrayList();
        simulator.setBottomEmitters(bottomEmitters);

        stage.addActor(bottomEmitterDisplays);
        for (int i = 0; i < 6; i++) {
            Emitter emitter = new Emitter();
            emitter.setRow(-1);
            emitter.setColumn(i);

            EmitterDisplayFacingUp emitterDisplay = new EmitterDisplayFacingUp(emitter);
            emitterDisplay.setX(50 * i);
            emitterDisplay.setY(0);
            emitterDisplay.setWidth(50);
            emitterDisplay.setHeight(50);

            bottomEmitterDisplays.addActor(emitterDisplay);
            bottomEmitters.add(emitter);
            emitter.setBulletFiredObservers(emitterDisplay::onBulletFired);
        }
        bottomEmitterDisplays.setY(0);
        bottomEmitterDisplays.setX(50);
    }

    private void createTopEmitters(Random r) {
        Group topEmitterDisplays = new Group();
        firedEmitters = new ArrayList<>();
        List<Emitter> topEmitters = new ArrayList();
        simulator.setTopEmitters(topEmitters);

        stage.addActor(topEmitterDisplays);
        for (int i = 0; i < 6; i++) {
            Emitter emitter = new Emitter();
            emitter.setRow(-1);
            emitter.setColumn(i);

            EmitterDisplayFacingDown emitterDisplay = new EmitterDisplayFacingDown(emitter);
            emitterDisplay.setX(50 * i);
            emitterDisplay.setY(0);
            emitterDisplay.setWidth(50);
            emitterDisplay.setHeight(50);

            topEmitterDisplays.addActor(emitterDisplay);
            topEmitters.add(emitter);
            emitter.setBulletFiredObservers(emitterDisplay::onBulletFired);
        }
        topEmitterDisplays.setY(350);
        topEmitterDisplays.setX(50);
    }

    private void createRightEmitters(Random r) {
        Group rightEmitterDisplays = new Group();
        List<Emitter> rightEmitters = new ArrayList<>();
        simulator.setRightEmitters(rightEmitters);

        stage.addActor(rightEmitterDisplays);
        for (int i = 0; i < 6; i++) {
            Emitter emitter = new Emitter();
            emitter.setRow(i);
            emitter.setColumn(-1);

            EmitterDisplayFacingLeft emitterDisplay = new EmitterDisplayFacingLeft(emitter);
            emitterDisplay.setX(0);
            emitterDisplay.setY(50 * i);
            emitterDisplay.setWidth(50);
            emitterDisplay.setHeight(50);

            rightEmitterDisplays.addActor(emitterDisplay);
            rightEmitters.add(emitter);
            emitter.setBulletFiredObservers(emitterDisplay::onBulletFired);
        }
        rightEmitterDisplays.setY(50);
        rightEmitterDisplays.setX(350);
    }

    private void createLeftEmitters(Random r) {
        Group leftEmitterDisplays = new Group();
        List<Emitter> leftEmitters = new ArrayList<>();
        simulator.setLeftEmitters(leftEmitters);

        createCellDisplays();
        stage.addActor(leftEmitterDisplays);
        for (int i = 0; i < 6; i++) {
            Emitter emitter = new Emitter();
            emitter.setRow(i);
            emitter.setColumn(-1);

            EmitterDisplayFacingRight emitterDisplay = new EmitterDisplayFacingRight(emitter);
            emitterDisplay.setX(0);
            emitterDisplay.setY(50 * i);
            emitterDisplay.setWidth(50);
            emitterDisplay.setHeight(50);

            leftEmitterDisplays.addActor(emitterDisplay);
            leftEmitters.add(emitter);

            emitter.setBulletFiredObservers(emitterDisplay::onBulletFired);
        }
        leftEmitterDisplays.setY(50);
    }

    private final void createCellDisplays() {
        Group cells = new Group();
        stage.addActor(cells);
        cells.setX(50);
        cells.setY(50);

        List<List<Cell>> rcCells = new ArrayList<>();
        simulator.setRcCells(rcCells);

        for (int row = 0; row < 6; row++) {
            Group rowGroup = new Group();
            cells.addActor(rowGroup);
            rowGroup.setY(row * 50);
            List<Cell> rowList = new ArrayList<Cell>();
            rcCells.add(rowList);
            for (int col = 0; col < 6; col++) {
                Cell cell = new Cell();
                rowList.add(cell);
                cell.setRow(row);
                cell.setCol(col);
                cell.setWorld(simulator);

                CellDisplay display = new CellDisplay(cell);
                display.setX(col * 50);
                display.setWidth(50);
                display.setHeight(50);
                display.setCellClickObserver(this::buyStuffFor);
                rowGroup.addActor(display);
            }
        }
    }

    private float timeSinceFired = 40f;
	@Override
	public void render () {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

        timeSinceFired += Gdx.graphics.getDeltaTime();
        if (timeSinceFired > 10) {
            simulator.simulateTurn();
            timeSinceFired = 0;
        }
	}
}
