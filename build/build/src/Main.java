
	
import javafx.application.Application;
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
			Parent root = FXMLLoader.load(getClass().getResource("view/ChatView.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			setDisplay(scene);
			NetworkManager.initNetwork();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private void setDisplay(final Scene scene) {
		DisplayManager.addDisplayListener(new DisplayListener() {
			@Override
			public void display(String message) {
				TextArea ta = (TextArea) scene.lookup("#chatbox");
				ta.appendText("\n" + message + "\n");
			}	
			@Override
			public void displaySecondary(String message) {
				ListView<String> connectedlist = (ListView<String>) scene.lookup("#connectedlist");
				ObservableList<String> items = connectedlist.getItems();
				if (!items.contains(message)) {
					items.add(message);
					connectedlist.setItems(items);
				}
			}
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
