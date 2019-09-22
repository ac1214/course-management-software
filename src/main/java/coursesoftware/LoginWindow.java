package coursesoftware;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class LoginWindow extends BaseWindow {
	TextField uNameField;
	PasswordField pwField;
	Button loginBtn;
	private boolean studentflag = false;

	/**
	 * Constructor that will initialize the properties of the page
	 */
	public LoginWindow() {
		this.windowTitle = "Login";

		this.grid = new GridPane();
		initPage(grid);
	}

	/**
	 * Initializes elements that will be on the GridPane
	 * 
	 * @param grid GridPane object to format elements
	 */
	private void initPage(GridPane grid) {
		GridPane subGrid = new GridPane();
		subGrid.setHgap(10);
		subGrid.setVgap(10);

		ImageView viewer = new ImageView();
		Image logo = new Image(LoginWindow.class.getResourceAsStream("/UofCLogo.png"));
		viewer.setImage(logo);
		viewer.setFitWidth(425);
		viewer.setFitHeight(240);

		grid.add(viewer, 0, 0);

		Label uNameLabel = new Label("User Name");
		uNameField = new TextField();

		Label pwLabel = new Label("Password");
		pwField = new PasswordField();

		loginBtn = new Button("Login");
		subGrid.add(uNameLabel, 0, 0);
		subGrid.add(uNameField, 0, 1);
		subGrid.add(pwLabel, 1, 0);
		subGrid.add(pwField, 1, 1);
		subGrid.add(loginBtn, 0, 2);
		subGrid.setAlignment(Pos.CENTER);
		grid.add(subGrid, 0, 1);

		grid.setAlignment(Pos.CENTER);
	}

	/**
	 * This method will handle inputs in the username/password fields and also
	 * handle when the user clicks the "login" button, when the user presses enter
	 * when in the username or password field
	 */
	public void addActions() {
		loginBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				checkUserInput();
			}
		});

		pwField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ENTER) {
					checkUserInput();
				}
			}
		});

		uNameField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ENTER) {
					checkUserInput();
				}
			}
		});

		loginBtn.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ENTER) {
					checkUserInput();
				}
			}
		});
	}

	/**
	 * This method will either successfully validate a user and open the admin
	 * console or popup an error message stating that the validation was
	 * unsuccessful
	 */
	private void checkUserInput() {
		String username = uNameField.getText();
		String password = pwField.getText();
		System.out.println("Verifying username: " + username);

		if (verifyUser(username, password)) {
			System.out.println("User Verified Successfully");

			if (studentflag) {
				StudentViewWindow studentView = new StudentViewWindow();
				openWindow(studentView, loginBtn);
			} else {
				ConsoleWindow consoleWindow = new ConsoleWindow();
				openWindow(consoleWindow, loginBtn);
			}
		}

		else {
			System.out.println("Failed to verify user");
			popupInvalidWarning();
		}
	}

	/**
	 * This function is from https://www.baeldung.com/sha-256-hashing-java
	 * 
	 * @param arr Array of bytes that contain the hash
	 * @return String value of the hashed password
	 */
	private static String bytesToHex(byte[] arr) {
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
	 * This method will verify is the username and corresponding hashed password
	 * exist in the database
	 * 
	 * @param username The username that was inputed
	 * @param pwHash   The hashed value of the password
	 * @return True if the user exists and the correct password was inputed, false
	 *         if the user doesn't exist or the wrong password was inputed.
	 */
	private boolean checkInAdminDatabase(String username, String pwHash) {
		if (username.length() == 0) {
			return false;
		}

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(ADMINFILE)));
			// Parse admin file data to check if entered username and password is valid
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

	private boolean checkInStudentDatabase(String username, String pwHash) {
		if (username.length() == 0) {
			return false;
		}

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(STUDENTFILE)));
			// Parse student file data to check if entered username and password is valid
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
	 * This method will popup a warning stating that the inputed username or
	 * password is invalid
	 */
	private void popupInvalidWarning() {
		Alert invalidUserPWAlert = new Alert(AlertType.ERROR);

		invalidUserPWAlert.setTitle("Invalid Credentials");
		invalidUserPWAlert.setHeaderText("Invalid Credentials");
		invalidUserPWAlert.setContentText("Failed to validate user!");

		invalidUserPWAlert.showAndWait();
	}

	/**
	 * This method will verify is the username and password input are a valid
	 * combination
	 * 
	 * @param username the username that the user has input
	 * @param password the password that the user has input
	 * @return Boolean value of whether the user was successfully validated
	 */
	public boolean verifyUser(String username, String password) {
		MessageDigest md;

		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
		byte[] hashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
		String pwHash = bytesToHex(hashBytes);

		if (checkInAdminDatabase(username, pwHash)) {
			return true;
		} else if (checkInStudentDatabase(username, pwHash)) {
			studentflag = true;
			return true;
		} else {
			return false;
		}
	}
}
