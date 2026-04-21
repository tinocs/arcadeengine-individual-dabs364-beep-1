package breakout;

import engine.Actor;
import javafx.scene.image.Image;

public class Ball extends Actor {
    private int dx;
    private int dy;

    private static final Image BALL_IMAGE = new Image(Ball.class.getResource("/breakoutresources/ball.png").toString());

    public Ball() {
        setImage(BALL_IMAGE);
        dx = 5;
        dy = 5;
    }

    @Override
    public void act(long now) {
        move(dx, dy);
        //bounce (top left)
        if(getX() <= 0) {
            dx = -dx;
        }
        if(getY() <= 0) {
            dy = -dy;
        }
        if(getX() + getImage().getWidth() >= getWorld().getWidth()) {
            dx= -dx;
        }
        if(getY() + getImage().getHeight() >= getWorld().getHeight()) {
            dy = -dy;
        }
        if (getOneIntersectingObject(Paddle.class) != null) {
            dy = -dy;
        }
    }

}