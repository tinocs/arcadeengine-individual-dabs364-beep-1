package breakout;

import engine.World;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

public class BallWorld extends World {

    private Score score;

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
        score = new Score();
        score.setX(20);
        score.setY(40);
        getChildren().add(score);

        Ball ball = new Ball();
        ball.setX((getPrefWidth() - ball.getWidth()) / 2);
        ball.setY((getPrefHeight() - ball.getHeight()) / 2);
        add(ball);

        for (int i = 0; i < 5; i++) {
            Brick brick = new Brick();
            brick.setX((getPrefWidth() - (brick.getWidth() * 5)) / 2 + i * brick.getWidth());
            brick.setY(80);
            add(brick);
        }

        Paddle paddle = new Paddle();
        paddle.setX((getPrefWidth() - paddle.getWidth()) / 2);
        paddle.setY(getPrefHeight() - paddle.getHeight() - 20);
        add(paddle);

        setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                paddle.setX(event.getX() - paddle.getWidth() / 2);
                paddle.handleEdges();
            }
        });
    }

    public Score getScore() {
        return score;
    }

}