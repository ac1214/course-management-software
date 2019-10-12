package coursesoftware;

import coursesoftware.database.ModifyUserData;
import coursesoftware.windows.AlertWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
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
                    if (AlertWindow.displayConfirmationWindowWithMessage("Password Field is Blank", "Incomplete Form", "Password field can not be blank,\nthese changes will not be saved")) {
                        EditAdminWindow editAdminWindow = new EditAdminWindow();
                        openWindow(editAdminWindow, finishBtn);
                    }
                } else if (!pwField.getText().equals(pwConfirmField.getText())) {
                    AlertWindow.displayErrorWindowWithMessage("Passwords do not match", "The passwords you have entered do not match", "Please enter your new password and try again");
                } else {
                    if (ModifyUserData.validateUser(username, oldPWField.getText()) == 1) {
                        if (pwField.getText().length() >= 5) {
                            changePassword(pwField.getText());
                            openWindow(new EditAdminWindow(), finishBtn);
                        } else {
                            AlertWindow.displayErrorWindow("Password is too short", "The new password you have entered\nis too short, please choose a password \nlonger than 5 characters");
                        }
                    } else {
                        AlertWindow.displayErrorWindowWithMessage("Double check old password", "The old password you have entered is invalid", "Please enter your old password and try again");
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
        ModifyUserData.changePassword(username, newPassword);
    }
}
