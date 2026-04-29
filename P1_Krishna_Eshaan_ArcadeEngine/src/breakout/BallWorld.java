package breakout;

import engine.World;
import engine.Sound;
import engine.Actor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class BallWorld extends World {

    private Score score;
    private int level;
    private Score lives;
    private boolean isPaused = true;
    private boolean isGameOver = false;
    private boolean gameWon = false;
    private boolean mousePressed = false;
    private Text messageLabel;

    private Sound ballBounceSound;
    private Sound brickHitSound;
    private Sound loseLifeSound;
    private Sound gameWonSound;
    private Sound gameLostSound;

    public static javafx.stage.Stage mainStage;

    private ImageView backgroundView;
    private Image backgroundImage;

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
        if (isGameOver) {
            if (isKeyPressed(KeyCode.SPACE)) {
                stop();
                mainStage.setScene(Breakout.titleScene);
            }
            return;
        }
        if (isPaused) {
            if (isKeyPressed(KeyCode.SPACE)) {
                setPaused(false);
            }
        } else {
            if(getObjects(Brick.class).isEmpty()) {
                level++;
                if(level > 3) {
                    gameWonSound.play();
                    gameWon = true;
                    isGameOver = true;
                    showGameOverMessage("YOU WIN! Press SPACE or click to return to menu");
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
        initGame();
    }

    public void resetGame() {
        isPaused = true;
        isGameOver = false;
        gameWon = false;
        level = 1;
        score.setScore(0);
        lives.setScore(3);
        if (messageLabel != null) {
            getChildren().remove(messageLabel);
        }
    }

    public void clearWorld() {
        for (Actor actor : getObjects(Actor.class)) {
            getChildren().remove(actor);
        }
        if (messageLabel != null) {
            getChildren().remove(messageLabel);
        }
    }

    public void initGame() {
        backgroundImage = new Image(BallWorld.class.getResource("/breakoutresources/img.png").toString());
        backgroundView = new ImageView(backgroundImage);
        backgroundView.setX((getPrefWidth() - backgroundImage.getWidth()) / 2);
        backgroundView.setY(0);
        getChildren().add(backgroundView);

        score = new Score("Score: ");
        score.setX(20);
        score.setY(40);
        getChildren().add(score);

        lives = new Score("Lives: ");
        lives.setScore(3);
        lives.setX(getWidth()-100);
        lives.setY(40);
        getChildren().add(lives);

        Ball ball = new Ball();
        ball.setX((getPrefWidth() - ball.getWidth()) / 2);
        ball.setY((getPrefHeight() - ball.getHeight()) / 2);
        add(ball);

        Paddle paddle = new Paddle();
        paddle.setX((getPrefWidth() - paddle.getWidth()) / 2);
        paddle.setY(getPrefHeight() - paddle.getHeight() - 20);
        add(paddle);

        /*
        setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                paddle.setX(event.getX() - paddle.getWidth() / 2);
                paddle.handleEdges();
            }
        });
        */

        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mousePressed = true;
                if (isGameOver) {
                    stop();
                    mainStage.setScene(Breakout.titleScene);
                } else if (isPaused) {
                    setPaused(false);
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mousePressed = false;
            }
        });
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    private void showGameOverMessage(String message) {
        messageLabel = new Text(message);
        messageLabel.setFill(Color.BLACK);
        messageLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        messageLabel.setWrappingWidth(400);
        messageLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        messageLabel.setX(getWidth() / 2 - 200);
        messageLabel.setY(getHeight() / 2);
        getChildren().add(messageLabel);
        requestFocus();
    }

    public void setGameOver(boolean gameOver, boolean won) {
        this.isGameOver = gameOver;
        this.gameWon = won;
        if (gameOver) {
            if (won) {
                showGameOverMessage("YOU WIN! Press SPACE or click to return to menu");
            } else {
                showGameOverMessage("GAME OVER! Press SPACE or click to return to menu");
            }
        }
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

    public boolean isGameOver() {
        return isGameOver;
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
        getObjects(Brick.class).forEach(brick -> getChildren().remove(brick));
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

    public void scroll(double dx) {

        if (backgroundView != null) {
            double newBgX = backgroundView.getX() - dx;
            double minBgX = getWidth() - backgroundImage.getWidth();
            double maxBgX = 0;
            newBgX = Math.max(minBgX, Math.min(maxBgX, newBgX));
            backgroundView.setX(newBgX);
        }

        for (Actor actor : getObjects(Actor.class)) {
            actor.move(-dx, 0);
        }
    }

    public double getBackgroundX() {
        return backgroundView != null ? backgroundView.getX() : 0;
    }

    public double getBackgroundWidth() {
        return backgroundImage != null ? backgroundImage.getWidth() : getWidth();
    }

}