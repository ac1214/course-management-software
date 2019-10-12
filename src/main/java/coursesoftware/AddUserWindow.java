package coursesoftware;

import coursesoftware.database.ModifyUserData;
import coursesoftware.windows.AlertWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class AddUserWindow extends BaseWindow {
    private TextField usernameField = new TextField();
    private PasswordField pwField = new PasswordField();
    private PasswordField pwConfirmField = new PasswordField();
    private Button finishBtn = new Button("Finish");

    /**
     * Set window parameters. Initialize scene with grid elements
     */
    public AddUserWindow() {
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
     * @param username username entered into the textfield
     * @return true if the username is valid. False otherwise.
     */
    protected boolean validateUser(String username) {

        try {
            boolean userExists = ModifyUserData.userExists(username);

            if (userExists) {
                AlertWindow.displayErrorWindowWithMessage("", "", "Please enter a new unique username and try again");

                return false;
            }

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
                    AlertWindow.displayErrorWindow("Empty Fields", "The fields cannot be empty");
                } else if (!pwField.getText().equals(pwConfirmField.getText())) {
                    System.out.println(pwField.getText());
                    System.out.println(pwConfirmField.getText());
                    AlertWindow.displayErrorWindow("Passwords do not match", "The password you have entered\ndo not match");
                } else {
                    if (validateUser(usernameField.getText())) {
                        if (pwField.getText().length() >= 5) {
                            saveUser();
                            EditAdminWindow editAdminWindow = new EditAdminWindow();
                            openWindow(editAdminWindow, finishBtn);
                        } else {
                            AlertWindow.displayErrorWindow("Password is too short", "The password you have entered\nis too short, please choose a password \nlonger than 5 characters");
                        }
                    }
                }
            }
        });
    }

    /**
     * Parses the textfield containing the entered password and user name. Hashes
     * the password according to the SHA-256 specification. Writes new the user name
     * and password to the database.
     */
    private void saveUser() {
        String password = pwField.getText();
        String username = usernameField.getText();

        ModifyUserData.addUser(username, password, true);
    }
}
