package engine;

import javafx.scene.layout.Pane;
import java.lang.Class;

public class World extends Pane {

    private AnimationTimer timer;
    private boolean isRunning;
    private ArrayList<KeyCode> keysPressed;
    private boolean widthSet;
    private boolean heightSet;


    public abstract void act(long now) {

    }
    public void add(Actor actor) {
        getChildren().add(actor);
    }

    public <A extends Actor> List<A> getObjects(Class<A> cls) {
        for(A actor : getChildren()) {
            if(cls.isInstance(actor)) {
                return (A) actor;
            }
        }
    }

    public <A extends Actor> List<A> getObjectsAt(int x, int y, Class<A> cls) {
        for(A actor : getObjects(cls)) {
            if(actor.contains(x, y)) {
                return (A) actor;
            }
        }
    }

    public boolean isKeyPressed(KeyCode key) {
        return keysPressed.contains(key);
    }

    public boolean isStopped() {
        return !isRunning;
    }

    public void start() {
        isRunning = true;
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                act(now);
                for(Actor actor : getChildren()) {
                    actor.act(now);
                }
            }
        }
    }

    public void stop() {
        isRunning = false;
        timer.stop();
    }

}
