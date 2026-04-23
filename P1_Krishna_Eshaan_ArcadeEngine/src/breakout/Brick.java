package breakout;

import engine.Actor;
import javafx.scene.image.Image;

public class Brick extends Actor {

    private static final Image BRICK_IMAGE = new Image(Brick.class.getResource("/breakoutresources/brick.png").toString());

    public Brick() {
        setImage(BRICK_IMAGE);
    }

    @Override
    public void act(long now) {
    }
}

