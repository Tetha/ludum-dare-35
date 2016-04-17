package org.subquark.growing_blob;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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

    private Label simulationStatus;
    private TextButton skipButton;

    private Group needMoreMoneyDisplay;
    private Group currentlySimulatingDisplay;

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

        simulationStatus = new Label("-", uiSkin);
        stage.addActor(simulationStatus);
        simulationStatus.setX(0);
        simulationStatus.setY(450);

        createNotEnoughMoneyDisplay(uiSkin);
        createCurrentlySimulatingDisplay(uiSkin);

        BuildPointDisplay buildPointDisplay = new BuildPointDisplay(uiSkin, simulator::getPlayerBuildPoints);
        stage.addActor(buildPointDisplay);
        buildPointDisplay.setX(400);
        buildPointDisplay.setY(400);
        buildPointDisplay.setHeight(50);
        buildPointDisplay.setWidth(150);
        buildPointDisplay.setDebug(true);

        skipButton = new TextButton("Simulate", uiSkin);
        stage.addActor(skipButton);
        skipButton.setX(400);
        skipButton.setY(350);
        skipButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (simulator.isSimulating()) {
                    showCurrentlySimulatingError();
                } else {
                    simulator.simulateTurn();
                }
            }
        });

        shop = new ShopGroup(uiSkin);
        stage.addActor(shop);
        shop.setX(400);
        shop.setY(0);
        shop.setHeight(350);
        shop.setWidth(200);

        Emitter testEmitter = simulator.getLeftEmitters().get(3);
        testEmitter.setColor(EmitterColor.YELLOW);
        testEmitter.setIsSetup(true);
        testEmitter.setLevel(1);

        Emitter speedTestEmitter = simulator.getRightEmitters().get(4);
        speedTestEmitter.setColor(EmitterColor.BLUE);
        speedTestEmitter.setIsSetup(true);
        speedTestEmitter.setLevel(2);

        Emitter noiseEmitter = simulator.getBottomEmitters().get(2);
        noiseEmitter.setColor(EmitterColor.RED);
        noiseEmitter.setIsSetup(true);
        noiseEmitter.setLevel(3);

        Cell testCell = simulator.getCell(3, 2);
        testCell.setCellContent(new ParticleAbsorber(r));

        Cell routerCell = simulator.getCell(3, 3);
        routerCell.setCellContent(new EnergyRouter(r));

        Cell builderCell = simulator.getCell(2, 3);
        builderCell.setCellContent(CellContentType.BUILD_POINT_GENERATOR.instantiate(r));
    }

    private void showCurrentlySimulatingError() {
        currentlySimulatingDisplay.addAction(
                Actions.sequence(
                        Actions.moveTo(110, 425),
                        Actions.visible(true),
                        Actions.moveBy(-25, 0, 0.25f),
                        Actions.delay(0.25f),
                        Actions.visible(false)
                )
        );
    }
    private void createCurrentlySimulatingDisplay(Skin uiSkin) {
        currentlySimulatingDisplay = new Group();
        stage.addActor(currentlySimulatingDisplay);

        ArrowPointingLeft arrow = new ArrowPointingLeft();
        arrow.setColor(com.badlogic.gdx.graphics.Color.RED);
        arrow.setWidth(50);
        arrow.setHeight(25);
        arrow.setX(0);
        arrow.setY(15);
        currentlySimulatingDisplay.addActor(arrow);

        Label complaint = new Label("Currently working\nPlease wait!!", uiSkin);
        complaint.setX(50);
        currentlySimulatingDisplay.addActor(complaint);

        currentlySimulatingDisplay.setVisible(false);
    }

    private void createNotEnoughMoneyDisplay(Skin uiSkin) {
        needMoreMoneyDisplay = new Group();
        stage.addActor(needMoreMoneyDisplay);
        needMoreMoneyDisplay.setX(250);
        needMoreMoneyDisplay.setY(400);

        ArrowPointingRight arrow = new ArrowPointingRight();
        arrow.setColor(com.badlogic.gdx.graphics.Color.RED);
        arrow.setWidth(50);
        arrow.setHeight(25);
        arrow.setX(80);
        arrow.setY(15);
        needMoreMoneyDisplay.addActor(arrow);

        Label complaint = new Label("You need\nmore points!", uiSkin);
        needMoreMoneyDisplay.addActor(complaint);

        needMoreMoneyDisplay.setVisible(false);
    }

    private void buyStuffFor(Cell c) {
        if (simulator.isSimulating()) {
            showCurrentlySimulatingError();
            return;
        }
        CellContentType selectedType = shop.getSelectedCellType();
        if (simulator.getPlayerBuildPoints() >= selectedType.getCost()) {
            c.setCellContent(selectedType.instantiate(r));
            simulator.reducePlayerBuildPointsBy(selectedType.getCost());
        } else {
            needMoreMoneyDisplay.addAction(
                    Actions.sequence(
                            Actions.moveTo(250, 400),
                            Actions.visible(true),
                            Actions.moveBy(25, 0, 0.25f),
                            Actions.delay(0.25f),
                            Actions.visible(false),
                            Actions.moveBy(-25, 0)
                    )
            );
        }
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

	@Override
	public void render () {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());

        if (simulator.isSimulating()) {
            simulationStatus.setText("Simulating...");
        } else {
            simulationStatus.setText("Waiting...");
        }
		stage.draw();
	}
}
