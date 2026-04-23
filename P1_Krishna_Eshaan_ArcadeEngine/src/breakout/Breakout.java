package breakout;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Breakout extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("Breakout");
		BorderPane root = new BorderPane();
		BallWorld world = new BallWorld();
		root.setCenter(world);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		world.start();
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

