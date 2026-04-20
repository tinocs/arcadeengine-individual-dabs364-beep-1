package engine;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.Scene;

public abstract class World extends Pane {

    private AnimationTimer timer;
    private boolean isRunning;
    private List<KeyCode> keysPressed;

    public World() {
        keysPressed = new ArrayList<>();
        isRunning = false;

        setOnKeyPressed(event -> {
            if (!keysPressed.contains(event.getCode())) {
                keysPressed.add(event.getCode());
            }
        });
        setOnKeyReleased(event -> keysPressed.remove(event.getCode()));

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                act(now);
                for (Actor actor : getObjects(Actor.class)) {
                    actor.act(now);
                }
            }
        };
    }

    public abstract void act(long now);

    public void add(Actor actor) {
        getChildren().add(actor);
        actor.addedToWorld();
    }

    public void remove(Actor actor) {
        getChildren().remove(actor);
    }

    public <A extends Actor> List<A> getObjects(Class<A> cls) {
        List<A> result = new ArrayList<>();
        for (Node node : getChildren()) {
            if (cls.isInstance(node)) {
                result.add(cls.cast(node));
            }
        }
        return result;
    }

    public <A extends Actor> List<A> getObjectsAt(double x, double y, Class<A> cls) {
        List<A> result = new ArrayList<>();
        for (A actor : getObjects(cls)) {
            if (actor.getBoundsInParent().contains(x, y)) {
                result.add(actor);
            }
        }
        return result;
    }

    public boolean isKeyPressed(KeyCode key) {
        return keysPressed.contains(key);
    }

    public void start() {
        isRunning = true;
        timer.start();
    }

    public void stop() {
        isRunning = false;
        timer.stop();
    }

    public boolean isStopped() {
        return !isRunning;
    }
}
