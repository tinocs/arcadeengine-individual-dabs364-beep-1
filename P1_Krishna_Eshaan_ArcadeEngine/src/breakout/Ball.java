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
        BallWorld world = (BallWorld) getWorld();
        if (world.isPaused()) {
            Paddle paddle = world.getObjects(Paddle.class).get(0);
            if (paddle != null) {
                setX(paddle.getX() + paddle.getWidth() / 2 - getWidth() / 2);
                setY(paddle.getY() - getHeight() - 5);
            }
        } else {
            move(dx, dy);
            //bounce (top left)
            if(getX() <= 0) {
                dx = -dx;
            }
            if(getY() <= 0) {
                dy = -dy;
            }
            if(getX() + getImage().getWidth() >= world.getWidth()) {
                dx= -dx;
            }
            if(getY() + getImage().getHeight() >= world.getHeight()) {
                dy = -dy;
                world.getLives().setScore(world.getLives().getScore() - 1);
                world.setPaused(true);
                world.resetBallPosition();
            }
            if (getOneIntersectingObject(Paddle.class) != null) {
                dy = -dy;
            }
            Brick brick = getOneIntersectingObject(Brick.class);
            if (brick != null) {
                double ballX = getX() + getWidth() / 2;
                double ballY = getY() + getHeight() / 2;
                if (ballX >= brick.getX() && ballX <= brick.getX() + brick.getWidth()) {
                    dy = -dy;
                } else if (ballY >= brick.getY() && ballY <= brick.getY() + brick.getHeight()) {
                    dx = -dx;
                } else {
                    dx = -dx;
                    dy = -dy;
                }
                getWorld().remove(brick);
                world.getScore().setScore(world.getScore().getScore() + 100);
            }
        }
    }

}