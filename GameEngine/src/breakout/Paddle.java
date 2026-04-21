package breakout;

import engine.Actor;
import javafx.scene.image.Image;

public class Paddle extends Actor {

	private static final Image PADDLE_IMAGE = new Image(Paddle.class.getResource("/breakoutresources/paddle.png").toString());

	public Paddle() {
		setImage(PADDLE_IMAGE);
	}

	@Override
	public void act(long now) {
	}
}

