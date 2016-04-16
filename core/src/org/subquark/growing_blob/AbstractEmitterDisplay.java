package org.subquark.growing_blob;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class AbstractEmitterDisplay extends Actor {
    protected float travelTime(float distance) {
        return 1 * distance;
    }
}
