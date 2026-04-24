package breakout;

import engine.World;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class BallWorld extends World {

    private Score score;
    private int level;
    private Score lives;

    public BallWorld(int level) {
        setPrefSize(800, 600);
        this.level = level;
    }

    @Override
    public void act(long now) {
        if(getObjects(Brick.class).isEmpty()) {
            level++;
            if(level > 3) {
                //game over, main menu

            } else {
                loadFile(BallWorld.class.getResourceAsStream("/breakoutresources/level" + level + ".txt"));
            }
        }
    }

    @Override
    public void start() {
        onDimensionsInitialized();
        loadFile(BallWorld.class.getResourceAsStream("/breakoutresources/level"+level+".txt"));
        super.start();
    }

    @Override
    public void onDimensionsInitialized() {
        score = new Score("Score: ");
        score.setX(20);
        score.setY(40);
        getChildren().add(score);

        lives = new Score("Lives: ");
        lives.setX(getWidth()-100);
        lives.setY(40);
        getChildren().add(lives);

        Ball ball = new Ball();
        ball.setX((getPrefWidth() - ball.getWidth()) / 2);
        ball.setY((getPrefHeight() - ball.getHeight()) / 2);
        add(ball);

        for (int i = 0; i < 5; i++) {
            Brick brick = new Brick(1);
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

    public Score getLives() {
        return lives;
    }

    public void loadFile(InputStream stream) {
        //remove all bricks
        getObjects(Brick.class).forEach(brick -> getChildren().remove(brick));
        //load bricks from file (0 = empty, 1 = blue, 2 = yellow)
        Scanner sc = new Scanner(stream);
        int rows = sc.nextInt();
        int cols = sc.nextInt();
        Brick temp = new Brick(1);
        double brickWidth = temp.getWidth();
        double brickHeight = temp.getHeight();
        double xOffset = (getPrefWidth() - cols * brickWidth) / 2;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int x = sc.nextInt();
                if (x == 1 || x == 2) {
                    Brick brick = new Brick(x);
                    brick.setX(xOffset + j * brickWidth);
                    brick.setY(i * brickHeight);
                    add(brick);
                }
            }
        }
        sc.close();
    }

}