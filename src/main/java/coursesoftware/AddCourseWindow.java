package coursesoftware;

import coursesoftware.database.DataModify;
import coursesoftware.datatypes.Course;
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
        // Get the course that is input
        Course inputCourse = getCourseFromInput();

        // Check if the input course is valid
        if(validateCourse(inputCourse)) {
            // Get the course from the input since it is valid
            // and insert the new course to the database
            DataModify.insertNewCourse(inputCourse);

            return true;
        }

        return false;
    }

    /**
     * This method will validate inputs from the text fields in the windows and make sure text has actually been input
     * in the fields
     * @return boolean value of whether the input is a valid course or not
     */
    private boolean validateCourse(Course course) {
        String courseID = course.getCourseID();
        String department = course.getCourseDep();
        String courseNum = course.getCourseNum();
        String courseName = course.getCourseName();
        String prerequisites = course.getPrerequisites();
        String antirequisites = course.getAntirequisites();

        // Ensure all of the fields are filled in
        if (!courseID.equals("") && !(department == null) && !courseNum.equals("") && !courseName.equals("")) {
            ArrayList<String> preAndAntirequisites = getPreAndAntiRequsites(prerequisites, antirequisites);

            if (validateCourses(courseID, courseNum, preAndAntirequisites, false)) {
                return true;
            } else {
                return false;
            }
        } else {
            return AlertWindow.displayConfirmationWindowWithMessage("Error in adding course", "Incomplete Form", "Course ID, Department, Number, and Name \ncan not be left blank \nThis course will not be saved");
        }
    }

    /**
     * This method will get a list of pre and antirequisites when given a string of pre and antirequisites
     * @param prerequisites string of prerequisites
     * @param antirequisites string of antirequisites
     * @return list of courses that were in the pre and antirequisites
     */
    private ArrayList<String> getPreAndAntiRequsites(String prerequisites, String antirequisites) {
        ArrayList<String> courses = new ArrayList<>();

        if (!prerequisites.equals("")) {
            courses.addAll(new ArrayList<>(Arrays.asList(prerequisites.split(","))));
        }
        if (!antirequisites.equals("")) {
            courses.addAll(new ArrayList<>(Arrays.asList(antirequisites.split(","))));
        }

        return courses;
    }

    /**
     * This method will get the input courses from the text fields in the window
     * @return A course iff it is in a valid format
     */
    private Course getCourseFromInput() {
        String courseID = courseIDField.getText();
        String department = departmentSelection.getValue();
        String courseNum = courseNumField.getText();
        String courseName = courseNameField.getText();
        String prerequisites = prerequisitesField.getText();
        String antirequisites = antirequisitesField.getText();
        String courseDescString = courseDesc.getText();

        return new Course(courseID, department, courseNum, courseName, prerequisites, antirequisites, courseDescString);
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
