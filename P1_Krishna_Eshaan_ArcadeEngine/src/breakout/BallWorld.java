package breakout;

import engine.World;
import engine.Sound;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class BallWorld extends World {

    private Score score;
    private int level;
    private Score lives;
    private boolean isPaused = true;

    private Sound ballBounceSound;
    private Sound brickHitSound;
    private Sound loseLifeSound;
    private Sound gameWonSound;
    private Sound gameLostSound;

    public BallWorld(int level) {
        setPrefSize(800, 600);
        this.level = level;

        ballBounceSound = new Sound("ballbounceresources/ball_bounce.wav");
        brickHitSound = new Sound("ballbounceresources/brick_hit.wav");
        loseLifeSound = new Sound("ballbounceresources/lose_life.wav");
        gameWonSound = new Sound("ballbounceresources/game_won.wav");
        gameLostSound = new Sound("ballbounceresources/game_lost.wav");
    }

    @Override
    public void act(long now) {
        if (isPaused) {
            if (isKeyPressed(KeyCode.SPACE)) {
                setPaused(false);
            }
        } else {
            if(getObjects(Brick.class).isEmpty()) {
                level++;
                if(level > 3) {
                    gameWonSound.play();
                    level = 1;
                    loadFile(BallWorld.class.getResourceAsStream("/breakoutresources/level" + level + ".txt"));
                    setPaused(true);
                    resetBallPosition();
                } else {
                    loadFile(BallWorld.class.getResourceAsStream("/breakoutresources/level" + level + ".txt"));
                    setPaused(true);
                    resetBallPosition();
                }
            }
        }
    }

    @Override
    public void start() {
        onDimensionsInitialized();
        loadFile(BallWorld.class.getResourceAsStream("/breakoutresources/level"+level+".txt"));
        resetBallPosition();
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

        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isPaused) {
                    setPaused(false);
                }
            }
        });
    }

    public Score getScore() {
        return score;
    }

    public Score getLives() {
        return lives;
    }

    public void playBallBounce() {
        ballBounceSound.play();
    }

    public void playBrickHit() {
        brickHitSound.play();
    }

    public void playLoseLife() {
        loseLifeSound.play();
    }

    public void playGameWon() {
        gameWonSound.play();
    }

    public void playGameLost() {
        gameLostSound.play();
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    public void resetBallPosition() {
        for (Ball ball : getObjects(Ball.class)) {
            Paddle paddle = getObjects(Paddle.class).get(0);
            ball.setX(paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2);
            ball.setY(paddle.getY() - ball.getHeight() - 5);
        }
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