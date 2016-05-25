

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import network.NetworkManager;

import java.io.File;

import display.DisplayListener;
import display.DisplayManager;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("view/StartUpView.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			NetworkManager.initNetwork();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	@Override
	public void stop() {
		NetworkManager.closeNetwork();
	}

	

	public static void main(String[] args) {
		launch(args);
	}
}
