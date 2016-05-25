package controller;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import network.NetworkManager;
import util.Contact;

public class ChatController {
	@FXML private TextArea chatbox;
	@FXML private TextArea inputbox;
	@FXML private TextField idbox;
	@FXML private TextField addressfield;
	@FXML private TextField portfield;
	@FXML private ListView<String> connectedlist;
	@FXML private ListView<Contact> contactlist;
	
	public void initialize() {
		connectedlist.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		idbox.setText(NetworkManager.getID());
		ArrayList<Contact> temp = new ArrayList<Contact>();
		//TODO initialize contact list from DB or some other persistent source
		temp.add(new Contact("self", "127.0.0.1", 23123));
		temp.add(new Contact("laptop", "192.168.0.20", 23124));
		
		ObservableList<Contact> clist = FXCollections.observableList(temp);
		contactlist.setItems(clist);
		
		contactlist.setCellFactory(new Callback<ListView<Contact>, ListCell<Contact>>() {

			@Override
			public ListCell<Contact> call(ListView<Contact> param) {
				ListCell<Contact> cell = new ListCell<Contact>() {
					@Override
					protected void updateItem(Contact contact, boolean bln) {
                        super.updateItem(contact, bln);
                        if (contact != null) {
                            setText(contact.toString());
                        }
                    } 
				};
				return cell;
			}
		});
		
		contactlist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contact>() {

			@Override
			public void changed(ObservableValue<? extends Contact> observable, Contact oldValue, Contact newValue) {
				addressfield.setText(newValue.getAddress());
				portfield.setText(new Integer(newValue.getPort()).toString());
			}
		});
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
	
	@FXML public void callUsers() {
		ObservableList<String> ids = connectedlist.getSelectionModel().getSelectedItems();
		for (String id : ids) {
			NetworkManager.callUser(id);
		}
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
