package breakout;

import engine.Actor;
import javafx.scene.image.Image;

public class Brick extends Actor {

    private static final Image BRICK_IMAGE = new Image(Brick.class.getResource("/breakoutresources/brick.png").toString());
    private static final Image BRICK_IMAGE2 = new Image(Brick.class.getResource("/breakoutresources/brick2.png").toString());

    public Brick(int type) {
        setImage(type == 1 ? BRICK_IMAGE : BRICK_IMAGE2);
    }

    @Override
    public void act(long now) {
    }
}

