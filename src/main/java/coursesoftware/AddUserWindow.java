package coursesoftware;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class AddUserWindow extends BaseWindow {
	private TextField usernameField = new TextField();
	private PasswordField pwField = new PasswordField();
	private PasswordField pwConfirmField = new PasswordField();
	private Button finishBtn = new Button("Finish");

	/**
	 * Set window parameters. Initialize scene with grid elements
	 * 
	 */
	public AddUserWindow() {
		this.windowHeight = 500;
		this.windowWidth = 500;
		this.windowTitle = "Add Admin User";

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
		Label passwordLabel = new Label("Password: ");
		Label confirmPWLabel = new Label("Confirm Password: ");

		grid.add(usernameLabel, 0, 0);
		grid.add(usernameField, 1, 0);
		grid.add(passwordLabel, 0, 1);
		grid.add(pwField, 1, 1);
		grid.add(confirmPWLabel, 0, 2);
		grid.add(pwConfirmField, 1, 2);
		grid.add(finishBtn, 0, 3);
	}

	/**
	 * Parses user name text field and compares it to other user names in the admin
	 * file.
	 * 
	 * @param userName username entered into the textfield
	 * @return true if the username is valid. False otherwise.
	 */
	protected boolean validateUser(String userName) {
		Alert alert = new Alert(AlertType.ERROR);

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(ADMINFILE)));

			String line;
			while ((line = br.readLine()) != null) {
				String[] user = line.split(";");
				String username = user[0];
				if (username.equals(userName)) {
					br.close();
					alert.setHeaderText("Make sure that the Username is unique");
					alert.setContentText("Please enter a new Username and try again");

					alert.showAndWait();
					return false;
				}
			}
			br.close();
		} catch (Exception e) {
			// File not found
		}

		return true;
	}

	/**
	 * Adds alerts, validates users and changes windows when clicking on buttons
	 */
	@Override
	public void addActions() {
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (pwField.getText().equals("") || usernameField.getText().equals("")
						|| pwConfirmField.getText().equals("")) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Empty Fields");
					alert.setHeaderText("The fields cannot be empty");
					alert.showAndWait();
				} else if (!pwField.getText().equals(pwConfirmField.getText())) {
					System.out.println(pwField.getText());
					System.out.println(pwConfirmField.getText());
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Passwords do not match");
					alert.setHeaderText("The password you have entered\ndo not match");
					alert.showAndWait();
				} else {
					if (validateUser(usernameField.getText())) {
						saveUser();
						EditAdminWindow editAdminWindow = new EditAdminWindow();
						openWindow(editAdminWindow, finishBtn);
					}
				}
			}
		});
	}

	/**
	 * This function is from https://www.baeldung.com/sha-256-hashing-java
	 * 
	 * @param arr Array of bytes that contain the hash
	 * @return String value of the hashed password
	 */
	private String bytesToHex(byte[] arr) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			String hex = Integer.toHexString(0xff & arr[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

	/**
	 * Parses the textfield containing the entered password and user name. Hashes
	 * the password according to the SHA-256 specification. Writes new the user name
	 * and password to the admin file.
	 */
	private void saveUser() {
		MessageDigest md = null;
		String password = pwField.getText();

		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] hashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
		String pwHash = bytesToHex(hashBytes);

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(ADMINFILE, true));

			bw.write(usernameField.getText() + ";" + pwHash + "\n");
			bw.close();
		} catch (IOException e) {
			System.out.println("File can not be opened");
		}
	}
}
