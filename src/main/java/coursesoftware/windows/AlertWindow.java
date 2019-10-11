package coursesoftware.windows;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertWindow {
    /**
     * Create an alert window with a title and header
     * @param alertTitle Title of the alert window
     * @param alertHeader Header of the alert window
     * @param alertType Type of the alert window to be displayed
     * @return Button in the alert
     */
    public static Optional<ButtonType> displayAlertWindow(String alertTitle, String alertHeader, Alert.AlertType alertType) {
        return displayAlertWindowWithMessage(alertTitle, alertHeader, "", alertType); // Create and alert window with an empty message
    }

    /**
     * Create an alert window with a message
     * @param alertTitle Title of the alert window
     * @param alertHeader Header of the alert window
     * @param alertContent Content of the alert window
     * @param alertType Type of the alert window to be displayed
     * @return Button in the alert
     */
    public static Optional<ButtonType> displayAlertWindowWithMessage(String alertTitle, String alertHeader, String alertContent, Alert.AlertType alertType) {
        Alert alertWindow = new Alert(alertType);

        alertWindow.setTitle(alertTitle);
        alertWindow.setHeaderText(alertHeader);
        alertWindow.setContentText(alertContent);

        Optional<ButtonType> result = alertWindow.showAndWait();
        return result;
    }

    /**
     * Create an alert window with a message
     * @param alertTitle Title of the alert window
     * @param alertHeader Header of the alert window
     */
    public static void displayErrorWindow(String alertTitle, String alertHeader) {
        displayAlertWindow(alertTitle, alertHeader, Alert.AlertType.ERROR);
    }

    /**
     * Create an alert window with a message
     * @param alertTitle Title of the alert window
     * @param alertHeader Header of the alert window
     * @param alertContent Content of the alert window
     */
    public static void displayErrorWindowWithMessage(String alertTitle, String alertHeader, String alertContent) {
        displayAlertWindowWithMessage(alertTitle, alertHeader, alertContent, Alert.AlertType.ERROR);
    }

    /**
     * Create an alert window with a message
     * @param alertTitle Title of the alert window
     * @param alertHeader Header of the alert window
     * @return Boolean value of whether the "OK" button was clicked
     */
    public static boolean displayConfirmationWindow(String alertTitle, String alertHeader) {
        return displayAlertWindow(alertTitle, alertHeader, Alert.AlertType.CONFIRMATION).get() == ButtonType.OK;
    }

    /**
     * Create an alert window with a message
     * @param alertTitle Title of the alert window
     * @param alertHeader Header of the alert window
     * @param alertContent Content of the alert window
     * @return Boolean value of whether the "OK" button was clicked
     */
    public static boolean displayConfirmationWindowWithMessage(String alertTitle, String alertHeader, String alertContent) {
        return displayAlertWindowWithMessage(alertTitle, alertHeader, alertContent, Alert.AlertType.CONFIRMATION).get() == ButtonType.OK;
    }
}
