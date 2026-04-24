package breakout;

import engine.Actor;
import javafx.scene.image.Image;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.ParallelTransition;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Brick extends Actor {

    private static final Image BRICK_IMAGE = new Image(Brick.class.getResource("/breakoutresources/brick.png").toString());
    private static final Image BRICK_IMAGE2 = new Image(Brick.class.getResource("/breakoutresources/brick2.png").toString());

    public Brick(int type) {
        setImage(type == 1 ? BRICK_IMAGE : BRICK_IMAGE2);
    }

    @Override
    public void act(long now) {
    }

    public void destroy() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), this);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(200), this);
        scaleOut.setFromX(1.0);
        scaleOut.setFromY(1.0);
        scaleOut.setToX(0.5);
        scaleOut.setToY(0.5);

        ParallelTransition animation = new ParallelTransition(fadeOut, scaleOut);
        animation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getWorld().remove(Brick.this);
            }
        });
        animation.play();
    }
}

