package coursesoftware;

import coursesoftware.database.DataModify;
import coursesoftware.datatypes.Program;
import coursesoftware.windows.AlertWindow;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class EditProgWindow extends BaseWindow {
    // Buttons for window
    Button finishBtn = new Button("Finish");
    Button removeBtn = new Button("Remove Course");
    Button addBtn = new Button("Add Course");

    private TableView<Program> currentProgramTable = new TableView<>();
    private String thisProg;

    /**
     * Constructor that will initialize the properties of the page
     */
    public EditProgWindow(String progName) {
        thisProg = progName;
        this.windowTitle = "Edit Programs";

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
        currentProgramTable.setMinWidth(400);

        grid.add(currentProgramTable, 0, 0);

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
        currentProgramTable.getColumns().clear();

        TableColumn<Program, String> column = new TableColumn<>("Course");
        column.setCellValueFactory(new PropertyValueFactory<>("progID"));
        column.setMinWidth(405);
        currentProgramTable.getColumns().add(column);

        ObservableList<Program> list = DataModify.getPrograms();

        currentProgramTable.setItems(list);
    }

    /**
     * Add handlers for the buttons
     */
    @Override
    public void addActions() {
        finishBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ProgramWindow programWindow = new ProgramWindow();
                openWindow(programWindow, finishBtn);
            }
        });

        removeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeCoursePrompt();
            }
        });

        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AddToProgWindow addToProgWindow = new AddToProgWindow(thisProg);
                openWindow(addToProgWindow, addBtn);
            }
        });
    }

    /**
     * Prompts the user when removing a course
     */
    private void removeCoursePrompt() {
        if (currentProgramTable.getSelectionModel().getSelectedItem() != null) {
            Program selectedcourse = currentProgramTable.getSelectionModel().getSelectedItem();
            String courseID = selectedcourse.getProgID();

            if (AlertWindow.displayConfirmationWindowWithMessage("Remove Course", "The course \"" + courseID + "\" will be removed", "Would you like to continue?")) {
                removeCourse(courseID);
            }
        } else {
            AlertWindow.displayErrorWindow("Select a Course", "Please select a Course that\n you would like to remove");
        }
    }

    /**
     * Removes course from the file
     *
     * @param courseID ID of the course that should be removed
     */
    private void removeCourse(String courseID) {
        // TODO: Implement removal of courses from program, i.e. remove from the list of courses in the database

        initTable();
    }

}
