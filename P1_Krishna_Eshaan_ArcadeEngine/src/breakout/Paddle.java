package breakout;

import engine.Actor;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Paddle extends Actor {

	private static final Image PADDLE_IMAGE = new Image(Paddle.class.getResource("/breakoutresources/paddle.png").toString());

	public Paddle() {
		setImage(PADDLE_IMAGE);
	}

	@Override
	public void act(long now) {
		if (getWorld() != null) {
			BallWorld world = (BallWorld) getWorld();
			if (world.isKeyPressed(KeyCode.LEFT)) {
				move(-5, 0);
				if (getX() < world.getWidth() / 2) {
					world.scroll(-3);
				}
			}
			if (world.isKeyPressed(KeyCode.RIGHT)) {
				move(5, 0);
				if (getX() > world.getWidth() / 2) {
					world.scroll(3);
				}
			}
			handleEdges();
		}
	}

	public void handleEdges() {
		BallWorld world = (BallWorld) getWorld();
		double bgLeft = world.getBackgroundX();
		double bgRight = bgLeft + world.getBackgroundWidth();
		double paddleWidth = getImage().getWidth();

		if (getX() < bgLeft) {
			setX(bgLeft);
		}
		if (getX() + paddleWidth > bgRight) {
			setX(bgRight - paddleWidth);
		}
	}

}

