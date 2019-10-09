package coursesoftware;

import coursesoftware.database.DataModify;
import coursesoftware.datatypes.Program;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class EditDeptWindow extends BaseWindow {
    Button finishBtn = new Button("Finish");
    Button removeBtn = new Button("Remove Program");
    Button addBtn = new Button("Add Program");

    private TableView<Program> currentDepartmentTable = new TableView<>();
    private String thisDept;

    /**
     * Constructor that will initialize the properties of the page
     */
    public EditDeptWindow(String deptName) {
        thisDept = deptName;
        this.windowHeight = 500;
        this.windowWidth = 500;
        this.windowTitle = "Edit Department";

        this.grid = new GridPane();

        initPage(grid);
    }

    /**
     * Initializes elements that will be on the GridPane
     *
     * @param grid GridPane object to add to
     */
    private void initPage(GridPane grid) {
        initTable();
        currentDepartmentTable.setMinWidth(400);

        grid.add(currentDepartmentTable, 0, 0);

        GridPane subGrid = new GridPane();
        subGrid.setHgap(10);
        subGrid.setVgap(10);

        double width = (410 - 20) / 3;
        addBtn.setMinWidth(width);
        removeBtn.setMinWidth(width);
        finishBtn.setMinWidth(width);

        subGrid.add(addBtn, 0, 0);
        subGrid.add(removeBtn, 1, 0);
        subGrid.add(finishBtn, 2, 0);
        subGrid.setAlignment(Pos.CENTER);

        grid.add(subGrid, 0, 1);
        grid.setAlignment(Pos.CENTER);
    }

    /**
     * Initializes table in the JavaFX window
     */
    private void initTable() {
        currentDepartmentTable.getColumns().clear();

        TableColumn<Program, String> column = new TableColumn<>("Program");
        column.setCellValueFactory(new PropertyValueFactory<>("progID"));
        column.setMinWidth(405);
        currentDepartmentTable.getColumns().add(column);

        ObservableList<Program> list = DataModify.getPrograms();

        currentDepartmentTable.setItems(list);
    }

    /**
     * Add handlers for the buttons
     */
    @Override
    public void addActions() {
        finishBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DepartmentWindow departmentWindow = new DepartmentWindow();

                openWindow(departmentWindow, finishBtn);
            }
        });

        removeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeProgPrompt();
            }
        });

        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addProg();
            }
        });
    }

    /**
     * Adds a program to a file
     */
    private void addProg() {
        TextInputDialog addProgramPrompt = new TextInputDialog();
        addProgramPrompt.setTitle("Add Program");
        addProgramPrompt.setHeaderText("Add a Program to " + thisDept);
        addProgramPrompt.setContentText("Input Program Name:");
        Optional<String> result = addProgramPrompt.showAndWait();
        if (result.isPresent()) {
            validateProg(result.get());
        }
    }

    private void addProgToFile(String programName) {
        DataModify.insertNewProgram(programName, thisDept);

        initTable();
    }

    /**
     * This method will validate a program name and make sure that programs with
     * duplicate names are not added to the file
     *
     * @param newProgName program name that should be validated and then added to
     *                    the file
     */
    private void validateProg(String newProgName) {
        DataModify.checkProgramExists(newProgName);
        addProgToFile(newProgName);
    }

    /**
     * This method will pop-up a window to verify if the selected program should be
     * removed
     */
    private void removeProgPrompt() {
        if (currentDepartmentTable.getSelectionModel().getSelectedItem() != null) {
            Program selectedProgram = currentDepartmentTable.getSelectionModel().getSelectedItem();
            String progID = selectedProgram.getProgID();
            Alert removeCourseAlert = new Alert(AlertType.CONFIRMATION);
            removeCourseAlert.setTitle("Remove Program");
            removeCourseAlert.setHeaderText("The department \"" + progID + "\" will be removed");
            removeCourseAlert.setContentText("Would you like to continue?");

            Optional<ButtonType> result = removeCourseAlert.showAndWait();

            if (result.get() == ButtonType.OK) {
                removeProg(progID);
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Select a program");
            alert.setContentText("Please select a program that\n you would like to remove");

            alert.showAndWait();
        }
    }

    /**
     * This method will remove a program with a specified name
     *
     * @param progID The name of the program that should be removed
     */
    private void removeProg(String progID) {

        DataModify.removeProgram(progID);
        initTable();
    }
}
