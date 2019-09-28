package coursesoftware;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
import coursesoftware.database.DataModify;

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
		} else {
			System.out.println("Failed to verify user");
			popupInvalidWarning();
		}
	}

	/**
	 * This method will popup a warning stating that the input username or
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
		int result = DataModify.validateUser(username, password);

		if(result == 1) {
			studentflag = false;
			return true;
		} else if(result == 2) {
			studentflag = true;
			return true;
		} else {
			return false;
		}
	}
}
