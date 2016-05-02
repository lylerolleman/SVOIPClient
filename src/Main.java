

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
	
	@Override
	public void stop() {
		NetworkManager.closeNetwork();
	}

	private void setDisplay(final Scene scene) {
		DisplayManager.addDisplayListener(new DisplayListener() {
			@Override
			public void display(String message) {
				TextArea ta = (TextArea) scene.lookup("#chatbox");
				ta.appendText("\n" + message + "\n");
			}	
			@Override
			public void addUser(final String user) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						ListView<String> connectedlist = (ListView<String>) scene.lookup("#connectedlist");
						ObservableList<String> items = connectedlist.getItems();
						if (!items.contains(user)) {
							items.add(user);
							connectedlist.setItems(items);
						}
					}
				});
			}
			@Override
			public void removeUser(final String user) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						ListView<String> connectedlist = (ListView<String>) scene.lookup("#connectedlist");
						ObservableList<String> items = connectedlist.getItems();
						for (int i=0; i<items.size(); i++) {
							if (items.get(i).equals(user)) {
								items.remove(i);
							}
						}
						connectedlist.setItems(items);
					}
				});
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
