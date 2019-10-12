package coursesoftware;

import coursesoftware.database.ModifyCourseData;
import coursesoftware.datatypes.Course;
import coursesoftware.windows.AlertWindow;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class ViewProgramWindow extends BaseWindow {
    Button finishBtn = new Button("Finish");
    Button viewCourseBtn = new Button("View Course");
    private TableView<Course> currentProgramTable = new TableView<>();
    private String thisProg;

    /**
     * Constructor that will initialize the properties of the page
     */
    public ViewProgramWindow(String progName) {
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
        viewCourseBtn.setMinWidth(205);
        finishBtn.setMinWidth(205);

        grid.add(currentProgramTable, 0, 0);

        GridPane subGrid = new GridPane();
        subGrid.setHgap(10);
        subGrid.setVgap(10);

        subGrid.add(viewCourseBtn, 0, 0);
        subGrid.add(finishBtn, 1, 0);

        grid.add(subGrid, 0, 1);
        grid.setAlignment(Pos.CENTER);
    }

    /**
     * Initializes table in the JavaFX window
     */
    private void initTable() {
        currentProgramTable.getColumns().clear();

        TableColumn<Course, String> column = new TableColumn<>("Course");
        column.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        column.setMinWidth(415);
        currentProgramTable.getColumns().add(column);

        ObservableList<Course> list = ModifyCourseData.getCourses();

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
                StudentViewWindow studentViewWindow = new StudentViewWindow();
                openWindow(studentViewWindow, finishBtn);
            }
        });
        viewCourseBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (currentProgramTable.getSelectionModel().getSelectedItem() != null) {
                    Course selectedCourse = currentProgramTable.getSelectionModel().getSelectedItem();
                    String courseID = selectedCourse.getCourseID();
                    ViewCourseWindow viewCourseWindow = new ViewCourseWindow(courseID, thisProg);
                    openWindow(viewCourseWindow, viewCourseBtn);
                } else {
                    AlertWindow.displayErrorWindow("Select a course", "Please select a program that\n you would like to view");
                }
            }
        });
    }

}
