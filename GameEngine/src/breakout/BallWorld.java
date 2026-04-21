package breakout;

import engine.World;

public class BallWorld extends World {

    public BallWorld() {
        setPrefSize(800, 600);
    }

    @Override
    public void act(long now) {

    }

    @Override
    public void start() {
        onDimensionsInitialized();
        super.start();
    }

    @Override
    public void onDimensionsInitialized() {
        Ball ball = new Ball();
        ball.setX((getPrefWidth() - ball.getWidth()) / 2);
        ball.setY((getPrefHeight() - ball.getHeight()) / 2);
        add(ball);

        Paddle paddle = new Paddle();
        paddle.setX((getPrefWidth() - paddle.getWidth()) / 2);
        paddle.setY(getPrefHeight() - paddle.getHeight() - 20);
        add(paddle);
    }


}