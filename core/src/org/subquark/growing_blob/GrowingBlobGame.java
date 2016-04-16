package org.subquark.growing_blob;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrowingBlobGame extends ApplicationAdapter {
	private Stage stage;

    private List<Emitter> leftEmitters;
    private List<Emitter> rightEmitters;
    private List<Emitter> topEmitters;
    private List<Emitter> bottomEmitters;

    private List<EmitterDisplayFacingDown> firedEmitters;
	@Override
	public void create () {
        Random r = new Random(42);
		stage = new Stage(new ScreenViewport());

        createCellDisplays();

        Group leftEmitterDisplays = new Group();
        leftEmitters = new ArrayList();

        createCellDisplays();
        stage.addActor(leftEmitterDisplays);
        for (int i = 0; i < 6; i++) {
            Emitter emitter = new Emitter();
            emitter.setColor(Color.values()[r.nextInt(3)]);
            emitter.setLevel(r.nextInt(4));

            EmitterDisplayFacingRight emitterDisplay = new EmitterDisplayFacingRight(emitter);
            emitterDisplay.setX(0);
            emitterDisplay.setY(50 * i);
            emitterDisplay.setWidth(50);
            emitterDisplay.setHeight(50);

            leftEmitterDisplays.addActor(emitterDisplay);
            leftEmitters.add(emitter);
        }
        leftEmitterDisplays.setY(50);

        Group rightEmitterDisplays = new Group();
        rightEmitters = new ArrayList<>();

        stage.addActor(rightEmitterDisplays);
        for (int i = 0; i < 6; i++) {
            Emitter emitter = new Emitter();
            emitter.setColor(Color.values()[r.nextInt(3)]);
            emitter.setLevel(r.nextInt(4));

            EmitterDisplayFacingLeft emitterDisplay = new EmitterDisplayFacingLeft(emitter);
            emitterDisplay.setX(0);
            emitterDisplay.setY(50 * i);
            emitterDisplay.setWidth(50);
            emitterDisplay.setHeight(50);

            rightEmitterDisplays.addActor(emitterDisplay);
            rightEmitters.add(emitter);
        }
        rightEmitterDisplays.setY(50);
        rightEmitterDisplays.setX(350);

        Group topEmitterDisplays = new Group();
        firedEmitters = new ArrayList<>();
        topEmitters = new ArrayList();

        stage.addActor(topEmitterDisplays);
        for (int i = 0; i < 6; i++) {
            Emitter emitter = new Emitter();
            emitter.setColor(Color.values()[r.nextInt(3)]);
            emitter.setLevel(r.nextInt(4));

            EmitterDisplayFacingDown emitterDisplay = new EmitterDisplayFacingDown(emitter);
            emitterDisplay.setX(50 * i);
            emitterDisplay.setY(0);
            emitterDisplay.setWidth(50);
            emitterDisplay.setHeight(50);

            topEmitterDisplays.addActor(emitterDisplay);
            firedEmitters.add(emitterDisplay);
            topEmitters.add(emitter);
        }
        topEmitterDisplays.setY(350);
        topEmitterDisplays.setX(50);

        Group bottomEmitterDisplays = new Group();
        bottomEmitters = new ArrayList();

        stage.addActor(bottomEmitterDisplays);
        for (int i = 0; i < 6; i++) {
            Emitter emitter = new Emitter();
            emitter.setColor(Color.values()[r.nextInt(3)]);
            emitter.setLevel(r.nextInt(4));

            EmitterDisplayFacingUp emitterDisplay = new EmitterDisplayFacingUp(emitter);
            emitterDisplay.setX(50 * i);
            emitterDisplay.setY(0);
            emitterDisplay.setWidth(50);
            emitterDisplay.setHeight(50);

            bottomEmitterDisplays.addActor(emitterDisplay);
            bottomEmitters.add(emitter);
        }
        bottomEmitterDisplays.setY(0);
        bottomEmitterDisplays.setX(50);
	}

    private final void createCellDisplays() {
        Group cells = new Group();
        stage.addActor(cells);
        cells.setX(50);
        cells.setY(50);

        for (int row = 0; row < 6; row++) {
            Group rowGroup = new Group();
            cells.addActor(rowGroup);
            rowGroup.setY(row * 50);

            for (int col = 0; col < 6; col++) {
                CellDisplay display = new CellDisplay();
                display.setX(col * 50);
                display.setWidth(50);
                display.setHeight(50);
                rowGroup.addActor(display);
            }
        }
    }

    private float timeSinceFired = 0f;
	@Override
	public void render () {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

        timeSinceFired += Gdx.graphics.getDeltaTime();
        if (timeSinceFired > 1.5) {
            firedEmitters.forEach(emitter -> emitter.onFired());
            timeSinceFired = 0;
        }
	}
}
