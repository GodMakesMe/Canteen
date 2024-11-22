package iiitd.assignment4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
	@FXML
	private Label welcomeText;
	private Stage Stage;
	private Scene preScene;
	@FXML
	private void goToAdminMode(ActionEvent event) throws IOException {
//		Stage = getStage(event);
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminMode.fxml"));
//		Stage.setScene(new Scene(loader.load()));
//		Stage.show();
		gotToAnotherScreen(event, "AdminMode.fxml", "Byte Me - Admin Mode");
	}

	@FXML
	private void goToStudentMode(ActionEvent event) throws IOException {
		gotToAnotherScreen(event, "StudentMode.fxml", "Byte Me - Student Mode");
	}

	void savePreviousScreen(ActionEvent event) throws IOException {
		preScene = ((Node)event.getSource()).getScene();
	}
	Stage getStage(ActionEvent event) {
		if (Stage != null) { return Stage; }
		return (Stage)((Node)event.getSource()).getScene().getWindow();
	}
	void goToPreviousScreen(ActionEvent event) throws IOException {
		Stage = getStage(event);
		Stage.setScene(preScene);
	}

	void gotToAnotherScreen(ActionEvent event, String fxmlFile, String Title) throws IOException {
		Stage = getStage(event);
		savePreviousScreen(event);
		Stage.setScene(new Scene(new FXMLLoader(getClass().getResource(fxmlFile)).load()));
		Stage.setTitle(Title);
		Stage.show();
	}
	void gotToAnotherScreen(ActionEvent event, String fxmlFile) throws IOException {
		Stage = getStage(event);
		savePreviousScreen(event);
		Stage.setScene(new Scene(new FXMLLoader(getClass().getResource(fxmlFile)).load()));
		Stage.show();
	}

	@FXML
	protected void onHelloButtonClick() {
		welcomeText.setText("Welcome to JavaFX Application!");
	}
}