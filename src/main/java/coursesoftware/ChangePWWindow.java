package coursesoftware;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;

import coursesoftware.database.DataModify;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

import javax.xml.crypto.Data;

public class ChangePWWindow extends BaseWindow {
	String username;
	PasswordField oldPWField = new PasswordField();
	PasswordField pwField = new PasswordField();
	PasswordField pwConfirmField = new PasswordField();
	Button finishBtn = new Button("Finish");

	/**
	 * Set window parameters. Initialize scene with grid elements
	 * 
	 * @param username username for the current user who is changing passwords
	 */
	public ChangePWWindow(String username) {
		this.username = username;

		this.grid = new GridPane();
		initPage(grid);
	}

	/**
	 * Initializes elements that will be on the GridPane
	 * 
	 * @param grid GridPane object to add to
	 */
	private void initPage(GridPane grid) {
		Label usernameLabel = new Label("Username: ");
		Label unameLabel = new Label(username);
		Label oldPWLabel = new Label("Old Password: ");
		Label pwLabel = new Label("New Password: ");
		Label confirmPWLabel = new Label("Confirm New Password: ");
		grid.add(usernameLabel, 0, 0);
		grid.add(unameLabel, 1, 0);
		grid.add(oldPWLabel, 0, 1);
		grid.add(oldPWField, 1, 1);
		grid.add(pwLabel, 0, 2);
		grid.add(pwField, 1, 2);
		grid.add(confirmPWLabel, 0, 3);
		grid.add(pwConfirmField, 1, 3);

		grid.add(finishBtn, 0, 4);
	}

	/**
	 * Add alerts, validate user and change windows when using clickable elements
	 */
	@Override
	public void addActions() {
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (oldPWField.getText().equals("")) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Password Field is Blank");
					alert.setHeaderText("Incomplete Form");
					alert.setContentText("Password field can not be blank,\nthese changes will not be saved");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						EditAdminWindow editAdminWindow = new EditAdminWindow();
						openWindow(editAdminWindow, finishBtn);
					}
				} else if (!pwField.getText().equals(pwConfirmField.getText())) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Passwords do not match");
					alert.setHeaderText("The passwords you have entered do not match");
					alert.setContentText("Please enter your new password and try again");

					alert.showAndWait();
				} else {
					if (DataModify.validateUser(username, oldPWField.getText()) == 1) {
						if(pwField.getText().length() >= 5) {
							changePassword(pwField.getText());
							openWindow(new EditAdminWindow(), finishBtn);
						} else {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Password is too short");
							alert.setHeaderText("The new password you have entered\nis too short, please choose a password \nlonger than 5 characters");
							alert.showAndWait();
						}
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Double check old password");
						alert.setHeaderText("The old password you have entered is invalid");
						alert.setContentText("Please enter your old password and try again");

						alert.showAndWait();
					}
				}
			}
		});
	}

	/**
	 * This method will be used to change a password of a specific user
	 * 
	 * @param newPassword This is the new password to set the users password to
	 */
	private void changePassword(String newPassword) {
		DataModify.changePassword(username, newPassword);
	}
}
