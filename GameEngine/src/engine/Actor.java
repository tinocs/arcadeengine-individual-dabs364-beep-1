package engine;

import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;

public abstract class Actor extends ImageView {

    public Actor() {
    }

    public abstract void act(long now);

    public void addedToWorld() {
    }

    public double getWidth() {
        return getBoundsInParent().getWidth();
    }

    public double getHeight() {
        return getBoundsInParent().getHeight();
    }

    public <A extends Actor> List<A> getIntersectingObjects(Class<A> cls) {
        List<A> intersectingObjects = new ArrayList<>();
        World world = getWorld();
        if (world != null) {
            for (A actor : world.getObjects(cls)) {
                if (actor != this && getBoundsInParent().intersects(actor.getBoundsInParent())) {
                    intersectingObjects.add(actor);
                }
            }
        }
        return intersectingObjects;
    }

    public <A extends Actor> A getOneIntersectingObject(Class<A> cls) {
        List<A> intersecting = getIntersectingObjects(cls);
        if (intersecting.isEmpty()) {
            return null;
        }
        return intersecting.get(0);
    }

    public World getWorld() {
        try {
            return (World) getParent();
        } catch (Exception e) {
            return null;
        }
    }

    public void move(double dx, double dy) {
        setX(getX() + dx);
        setY(getY() + dy);
    }
}
