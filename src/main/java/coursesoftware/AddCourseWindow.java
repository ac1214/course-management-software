package coursesoftware;

import coursesoftware.database.DataModify;
import coursesoftware.windows.AlertWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Arrays;

public class AddCourseWindow extends BaseCourseWindow {
    Button finishBtn = new Button("Finish");
    TextField courseIDField = new TextField();
    ComboBox<String> departmentSelection = new ComboBox<>();
    TextField courseNameField = new TextField();
    TextField courseNumField = new TextField();
    TextField prerequisitesField = new TextField();
    TextField antirequisitesField = new TextField();
    TextArea courseDesc = new TextArea();

    /**
     * Initializes elements that will be on the GridPane
     */
    public AddCourseWindow() {
        super();
        this.windowTitle = "Add Course";
        this.windowLabel.setText("Add Course");

        ArrayList<String> departments = getDepartments();
        departmentSelection.getItems().addAll(departments);
        grid.add(courseIDField, 1, 1);
        grid.add(departmentSelection, 1, 2);
        grid.add(courseNumField, 1, 3);
        grid.add(courseNameField, 1, 4);
        grid.add(prerequisitesField, 1, 5);
        grid.add(antirequisitesField, 1, 6);
        courseDesc.setMaxWidth(200);
        courseDesc.setWrapText(true);
        grid.add(courseDesc, 1, 7);

        grid.add(finishBtn, 0, 8);
        grid.setAlignment(Pos.CENTER);
    }

    /**
     * Method parses departments from the database and returns them
     *
     * @return arrayList<String> Returns an array list of strings of departments
     * form the department list.
     */
    private ArrayList<String> getDepartments() {
        return DataModify.getDepartmentNameList();
    }

    /**
     * Parses information entered in the fields by the user, validates information
     * and calls writeCourse if valid.
     *
     * @return boolean return true if successfully saved a course. False otherwise
     */
    private boolean saveCourse() {
        String courseID = courseIDField.getText();
        String department = departmentSelection.getValue();
        String courseNum = courseNumField.getText();
        String courseName = courseNameField.getText();
        String prerequisites = prerequisitesField.getText();
        String antirequisites = antirequisitesField.getText();
        String courseDescString = String.join("NEWLINE", Arrays.asList(courseDesc.getText().split("\n")));

        if (!courseID.equals("") && !(department == null) && !courseNum.equals("") && !courseName.equals("")) {
            ArrayList<String> courses = new ArrayList<>();
            if (!prerequisites.equals("")) {
                courses.addAll(new ArrayList<>(Arrays.asList(prerequisites.split(","))));
            }
            if (!antirequisites.equals("")) {
                courses.addAll(new ArrayList<>(Arrays.asList(antirequisites.split(","))));
            }
            if (validateCourses(courseID, courseNum, courses, false)) {
                writeCourse(courseID, department, courseNum, courseName, prerequisites, antirequisites,
                        courseDescString);
                return true;
            } else {
                return false;
            }
        } else {
            return AlertWindow.displayConfirmationWindowWithMessage("Error in adding course", "Incomplete Form", "Course ID, Department, Number, and Name \ncan not be left blank \nThis course will not be saved");
        }
    }

    /**
     * Writes the course passed in from saveCourse and writes it to the course file.
     *
     * @param courseID         Course ID from text field
     * @param department       department from text field
     * @param courseNum        Course number from text field
     * @param courseName       Course name from text field
     * @param prerequisites    Course prerequisites from text field
     * @param antirequisites   Course antirequisites from text field
     * @param courseDescString Course description from text field
     */
    private void writeCourse(String courseID, String department, String courseNum, String courseName,
                             String prerequisites, String antirequisites, String courseDescString) {

        DataModify.insertNewCourse(courseID, department, Integer.valueOf(courseNum), courseName, prerequisites, antirequisites, courseDescString);
    }

    /**
     * Add actions for the buttons
     */
    @Override
    public void addActions() {
        finishBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (saveCourse()) {
                    CourseWindow courseWindow = new CourseWindow();
                    openWindow(courseWindow, finishBtn);
                }
            }
        });
    }
}
