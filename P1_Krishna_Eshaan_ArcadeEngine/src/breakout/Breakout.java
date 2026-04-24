package breakout;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Breakout extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("Breakout");

		BorderPane gameRoot = new BorderPane();
		BallWorld gameWorld = new BallWorld(1);
		gameRoot.setCenter(gameWorld);
		Scene gameScene = new Scene(gameRoot);

		VBox titleRoot = new VBox();
		titleRoot.setAlignment(Pos.CENTER);
		titleRoot.setPrefSize(800, 600);
		titleRoot.setSpacing(10);
		Label titleLabel = new Label("Breakout");
		titleLabel.setStyle("-fx-font-size: 60px; -fx-font-weight: bold;");
		Button playButton = new Button("Play");
		titleRoot.getChildren().addAll(titleLabel, playButton);
		Scene titleScene = new Scene(titleRoot);

		playButton.setOnAction(e -> {
			stage.setScene(gameScene);
			gameWorld.start();
		});

		stage.setScene(titleScene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
