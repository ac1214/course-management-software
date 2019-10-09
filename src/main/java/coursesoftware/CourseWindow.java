package coursesoftware;

import coursesoftware.database.DataModify;
import coursesoftware.datatypes.Course;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class CourseWindow extends BaseWindow {
    private TableView<Course> courseTable = new TableView<>();
    private Button addCourseBtn = new Button("Add New Course");
    private Button editCourseBtn = new Button("Edit Course");
    private Button removeCourseBtn = new Button("Remove Course");
    private Button finishBtn = new Button("Finish");

    /**
     * Constructor that will initialize the properties of the page
     */
    public CourseWindow() {
        this.windowHeight = 600;
        this.windowWidth = 1000;
        this.windowTitle = "Courses";

        this.grid = new GridPane();
        initTable();
        initPage(grid);
    }

    /**
     * This method will initialize a table containing course attributes with their
     * corresponding columns.
     */
    @SuppressWarnings("unchecked")
    private void initTable() {
        courseTable.setEditable(true);

        TableColumn<Course, String> courseID = new TableColumn<>("Course ID");
        courseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        TableColumn<Course, String> deptName = new TableColumn<>("Department");
        deptName.setCellValueFactory(new PropertyValueFactory<>("courseDep"));
        TableColumn<Course, String> courseNum = new TableColumn<>("Course#");
        courseNum.setCellValueFactory(new PropertyValueFactory<>("courseNum"));
        TableColumn<Course, String> courseName = new TableColumn<>("Course Name");
        courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        TableColumn<Course, String> prerequisites = new TableColumn<>("Prerequisites");
        prerequisites.setCellValueFactory(new PropertyValueFactory<>("prerequisites"));
        prerequisites.setMinWidth(135);
        TableColumn<Course, String> antirequisites = new TableColumn<>("Antirequisites");
        antirequisites.setCellValueFactory(new PropertyValueFactory<>("antirequisites"));
        antirequisites.setMinWidth(135);

        courseTable.getColumns().addAll(courseID, deptName, courseNum, courseName, prerequisites, antirequisites);

        addTableData();
    }

    /**
     * This method adds the data from the file to the table that is displayed in the
     * JavaFX window.
     */
    private void addTableData() {
        ObservableList<Course> list = DataModify.getCourses();
        courseTable.setItems(list);
    }

    /**
     * Initializes elements that will be on the GridPane
     *
     * @param grid GridPane object to add to
     */
    private void initPage(GridPane grid) {
        addCourseBtn.setMinWidth(150);
        editCourseBtn.setMinWidth(150);
        removeCourseBtn.setMinWidth(150);
        finishBtn.setMinWidth(150);

        courseTable.setMinWidth(400);
        courseTable.setMinHeight(540);

        courseTable.setMinWidth(810);
        GridPane subGrid = new GridPane();

        subGrid.setHgap(10);
        subGrid.setVgap(10);

        subGrid.add(addCourseBtn, 0, 0);
        subGrid.add(editCourseBtn, 0, 1);
        subGrid.add(removeCourseBtn, 0, 2);
        subGrid.add(finishBtn, 0, 3);

        grid.add(courseTable, 0, 0);
        grid.add(subGrid, 1, 0);
        grid.setAlignment(Pos.CENTER);
    }

    /**
     * Add handlers for the buttons
     */
    @Override
    public void addActions() {
        addCourseBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AddCourseWindow addCourseWindow = new AddCourseWindow();
                openWindow(addCourseWindow, addCourseBtn);
            }
        });

        editCourseBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (courseTable.getSelectionModel().getSelectedItem() != null) {
                    Course selectedcourse = courseTable.getSelectionModel().getSelectedItem();
                    String courseID = selectedcourse.getCourseID();
                    EditCourseWindow editCourseWindow = new EditCourseWindow(courseID);
                    openWindow(editCourseWindow, addCourseBtn);
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Select a Course");
                    alert.setContentText("Please select a course that you would like to edit");

                    alert.showAndWait();
                }

            }
        });

        removeCourseBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeCoursePrompt();
            }
        });

        finishBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ConsoleWindow consoleWindow = new ConsoleWindow();
                openWindow(consoleWindow, finishBtn);
            }
        });
    }

    /**
     * This method will prompt a popup window to ask the user to confirm if they
     * would like a selected course to be deleted.
     */
    private void removeCoursePrompt() {
        if (courseTable.getSelectionModel().getSelectedItem() != null) {
            Course selectedcourse = courseTable.getSelectionModel().getSelectedItem();
            String courseID = selectedcourse.getCourseID();
            Alert removeCourseAlert = new Alert(AlertType.CONFIRMATION);
            removeCourseAlert.setTitle("Remove Course");
            removeCourseAlert.setHeaderText("The course \"" + courseID + "\" will be removed");
            removeCourseAlert.setContentText("Would you like to continue?");

            Optional<ButtonType> result = removeCourseAlert.showAndWait();

            if (result.get() == ButtonType.OK) {
                removeCourse(courseID);
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Select a Course");
            alert.setContentText("Please select a course that\n you would like to remove");

            alert.showAndWait();
        }
    }

    /**
     * This method will remove a course from the database.
     *
     * @param courseID The ID of the course that should be removed.
     */
    private void removeCourse(String courseID) {
        DataModify.removeCourse(courseID);

        addTableData();
    }
}
