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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

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
					if (verifyUser(oldPWField.getText())) {
						changePassword(pwField.getText());
						openWindow(new EditAdminWindow(), finishBtn);
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
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(ADMINFILE)));
			String line;
			ArrayList<String> addToFile = new ArrayList<>();

			while ((line = br.readLine()) != null) {
				String[] user = line.split(";");
				if (user[0].equals(username)) {
					MessageDigest md = null;

					try {
						md = MessageDigest.getInstance("SHA-256");
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					}
					byte[] hashBytes = md.digest(newPassword.getBytes(StandardCharsets.UTF_8));
					String pwHash = bytesToHex(hashBytes);
					addToFile.add(user[0] + ";" + pwHash + "\n");
				} else {
					addToFile.add(user[0] + ";" + user[1] + "\n");
				}
			}
			br.close();

			BufferedWriter bw = new BufferedWriter(new FileWriter(ADMINFILE));

			for (String s : addToFile) {
				bw.write(s);
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
			// File not found
		}
	}

	/**
	 * This method will verify is the username and password input are a valid
	 * combination
	 * 
	 * @param username the username that the user has input
	 * @param password the password that the user has input
	 * @return Boolean value of whether the user was successfully validated
	 */
	public boolean verifyUser(String password) {
		MessageDigest md;

		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
		byte[] hashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
		String pwHash = bytesToHex(hashBytes);

		if (checkInDatabase(username, pwHash)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method will verify is the username and corresponding hashed password
	 * exist in the database
	 * 
	 * @param username The username that was inputed
	 * @param pwHash   The hashed value of the password
	 * @return True if the user exists and the correct password was inputed, false
	 *         if the user doesn't exist or the wrong password was inputed.
	 */
	private boolean checkInDatabase(String username, String pwHash) {
		if (username.length() == 0) {
			return false;
		}

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(ADMINFILE)));

			String line;
			while ((line = br.readLine()) != null) {
				String[] usernameAndHash = line.split(";");
				if (usernameAndHash[0].equals(username)) {
					if (usernameAndHash[1].equals(pwHash)) {
						br.close();
						return true;
					} else {
						br.close();
						return false;
					}
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
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
}
