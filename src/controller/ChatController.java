package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import network.NetworkManager;

public class ChatController {
	@FXML private TextArea chatbox;
	@FXML private TextArea inputbox;
	@FXML private TextField idbox;
	@FXML private TextField addressfield;
	@FXML private TextField portfield;
	@FXML private ListView<String> connectedlist;
	
	public void initialize() {
		connectedlist.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	@FXML public void buttonPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			String text = inputbox.getText();
			inputbox.clear();
			chatbox.appendText("Me: " + text);
			String id = NetworkManager.getID();
			ObservableList<String> ids = connectedlist.getSelectionModel().getSelectedItems();
			for (String cid : ids) {
				NetworkManager.sendMessage(cid, id, text.trim());
			}
		}
	}
	
	@FXML public void updateID() {
		NetworkManager.setID(idbox.getText());
	}
	
	@FXML public void connect() {
		Integer port = Integer.parseInt(portfield.getText());
		NetworkManager.establishConnection(NetworkManager.getID(), addressfield.getText(), port);
	}
	
	@FXML public void disconnect() {
		ObservableList<String> ids = connectedlist.getSelectionModel().getSelectedItems();
		ObservableList<String> items = connectedlist.getItems();
		for (int i=0; i<ids.size(); i++) {
			NetworkManager.sendDisconnect(ids.get(i));
			items.remove(ids.get(i));
		}
		connectedlist.setItems(items);
	}
}
