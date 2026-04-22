package breakout;

import engine.Actor;
import javafx.scene.input.KeyCode;

public class Paddle extends Actor {

	private static final Image PADDLE_IMAGE = new Image(Paddle.class.getResource("/breakoutresources/paddle.png").toString());

	public Paddle() {
		setImage(PADDLE_IMAGE);
	}

	@Override
	public void act(long now) {
	}
		if (getWorld() != null) {
			if (getWorld().isKeyPressed(KeyCode.LEFT)) {
				move(-5, 0);
			}
			if (getWorld().isKeyPressed(KeyCode.RIGHT)) {
				move(5, 0);
			}
		}
}

