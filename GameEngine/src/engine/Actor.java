package engine;

import javafx.scene.image.ImageView;


public class Actor extends ImageView {
    public abstract void act(long now) {

    }
    public void addedToWorld() {

    }

    public double getWidth() {
        return getImage().getWidth();
    }
    public double getHeight() {
        return getImage().getHeight();
    }
    public <A extends Actor> List<A> getIntersectingObjects(Class<A> cls) {
        List<A> intersectingObjects = new ArrayList<>();
        for(A actor : getWorld().getObjects(cls)) {
            if(actor != this && getBoundsInParent().intersects(actor.getBoundsInParent())) {
                intersectingObjects.add(actor);
            }
        }
        return intersectingObjects;
    }
    public <A extends Actor> getOneIntersectingObject(Class<A> cls) {
        return getIntersectingObjects(cls).get(0);
    }

    public World getWorld() {
        return (World) getParent();
    }

    public move(double dx, double dy) {
        setX(getX() + dx);
        setY(getY() + dy);
    }


}
