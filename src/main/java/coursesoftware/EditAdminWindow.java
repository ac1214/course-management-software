package coursesoftware;

import coursesoftware.database.DataModify;
import coursesoftware.datatypes.Admin;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class EditAdminWindow extends BaseWindow {
    private Button finishBtn = new Button("Finish");
    private Button addBtn = new Button("Add User");
    private Button chPWBtn = new Button("Change Password");
    private Button removeBtn = new Button("Remove User");
    private TableView<Admin> adminTable = new TableView<>();

    /**
     * Constructor that will initialize the properties of the page
     */
    public EditAdminWindow() {
        this.windowHeight = 500;
        this.windowWidth = 500;
        this.windowTitle = "Edit Administrators";

        this.grid = new GridPane();

        initTable();
        initPage(grid);
    }

    /**
     * Initializes table in the JavaFX window
     */
    private void initTable() {
        adminTable.getColumns().clear();
        adminTable.setEditable(true);

        TableColumn<Admin, String> admins = new TableColumn<Admin, String>("Administrators");
        admins.setCellValueFactory(new PropertyValueFactory<>("username"));
        admins.setMinWidth(405);
        adminTable.getColumns().add(admins);

        ObservableList<Admin> list = DataModify.getAdminUsers();

        adminTable.setItems(list);
    }

    /**
     * Initializes elements that will be on the GridPane
     *
     * @param grid GridPane object to add to
     */
    private void initPage(GridPane grid) {
        grid.add(adminTable, 0, 0);
        GridPane subGrid = new GridPane();
        subGrid.setHgap(10);

        double tableWidth = adminTable.getWidth();
        double buttonWidth = (tableWidth - 30) / 3;
        addBtn.setMinWidth(buttonWidth);
        chPWBtn.setMinWidth(buttonWidth);
        removeBtn.setMinWidth(buttonWidth);
        finishBtn.setMinWidth(buttonWidth);

        subGrid.add(addBtn, 0, 0);
        subGrid.add(chPWBtn, 1, 0);
        subGrid.add(removeBtn, 2, 0);
        subGrid.add(finishBtn, 3, 0);

        grid.add(subGrid, 0, 1);
        grid.setAlignment(Pos.CENTER);
    }

    /**
     * Add handlers for the buttons
     */
    @Override
    public void addActions() {
        finishBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ConsoleWindow consoleWindow = new ConsoleWindow();
                openWindow(consoleWindow, finishBtn);
            }
        });

        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AddUserWindow addUserWindow = new AddUserWindow();
                openWindow(addUserWindow, addBtn);
            }
        });

        chPWBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (adminTable.getSelectionModel().getSelectedItem() != null) {
                    Admin admin = adminTable.getSelectionModel().getSelectedItem();
                    ChangePWWindow changePWWindow = new ChangePWWindow(admin.getUsername());
                    openWindow(changePWWindow, chPWBtn);
                }

            }
        });

        removeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeUserPrompt();
            }
        });
    }

    /**
     * Pops up a prompt about removing a course to the administrator
     */
    private void removeUserPrompt() {
        if (adminTable.getItems().size() == 1) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Cannot remove user");
            alert.setContentText("There must be at least one administrator,\nthis user cannot be removed");

            alert.showAndWait();
        } else if (adminTable.getSelectionModel().getSelectedItem() != null) {
            Admin selectedUser = adminTable.getSelectionModel().getSelectedItem();
            String username = selectedUser.getUsername();
            Alert removeUserAlert = new Alert(AlertType.CONFIRMATION);
            removeUserAlert.setTitle("Remove User");
            removeUserAlert.setHeaderText("The user \"" + username + "\" will be removed");
            removeUserAlert.setContentText("Would you like to continue?");

            Optional<ButtonType> result = removeUserAlert.showAndWait();

            if (result.get() == ButtonType.OK) {
                removeUser(username);
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Select a User");
            alert.setContentText("Please select a user that\nyou would like to remove");

            alert.showAndWait();
        }

    }

    /**
     * This method will remove a user from the administrators file.
     *
     * @param username Username of the user to be removed
     */
    private void removeUser(String username) {
        DataModify.removeUser(username);

        initTable();
    }
}
