package controller;

import java.io.IOException;

import display.DisplayListener;
import display.DisplayManager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import network.NetworkManager;

public class LaunchController {
	@FXML private TextField idbox;
	@FXML private GridPane gp;
	
	@FXML public void launch() {
		NetworkManager.setID(idbox.getText());
		Stage stage = (Stage) gp.getScene().getWindow();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/ChatView.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setMaximized(true);
			setDisplay(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}
