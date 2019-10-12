package coursesoftware;

import coursesoftware.database.ModifyCourseData;
import coursesoftware.datatypes.Course;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;

public class EditCourseWindow extends BaseCourseWindow {
    TextArea courseDesc = new TextArea();
    private Button finishBtn = new Button("Finish");
    private String courseID = "";
    private Label courseIDNameLabel = new Label("");
    private Label departmentNameLabel = new Label("");
    private Label courseNumNameLabel = new Label("");
    private Label courseNameLabel = new Label("");
    private TextField prerequisitesField = new TextField();
    private TextField antirequisitesField = new TextField();

    /**
     * Constructor for EditCourseWindow class that will be used to edit and existing
     * course
     *
     * @param courseID Course ID to construct the EditCourseWindow window
     */
    public EditCourseWindow(String courseID) {
        super();
        this.windowTitle = "Edit Course";
        this.windowLabel.setText("Edit Course");
        this.courseID = courseID;
        setFields();

        grid.add(courseIDNameLabel, 1, 1);
        grid.add(departmentNameLabel, 1, 2);
        grid.add(courseNumNameLabel, 1, 3);
        grid.add(courseNameLabel, 1, 4);
        grid.add(prerequisitesField, 1, 5);
        grid.add(antirequisitesField, 1, 6);
        grid.add(courseDesc, 1, 7);
        courseDesc.setMaxWidth(200);
        courseDesc.setWrapText(true);
        grid.add(finishBtn, 0, 8);
        grid.setAlignment(Pos.CENTER);
    }

    public void setFields() {
        Course editingCourse = ModifyCourseData.getCourse(courseID);

        courseIDNameLabel.setText(editingCourse.getCourseID());
        departmentNameLabel.setText(editingCourse.getCourseDep());
        courseNumNameLabel.setText(editingCourse.getCourseNum());
        courseNameLabel.setText(editingCourse.getCourseName());
        prerequisitesField.setText(editingCourse.getPrerequisites());
        antirequisitesField.setText(editingCourse.getAntirequisites());
        courseDesc.setText(editingCourse.getDescription());
    }

    /**
     * This method will save an edited course and will update the course file with
     * the new information
     *
     * @return Returns whether the course was able to successfully update the file
     */
    public boolean editCourse() {
        String prerequisites = prerequisitesField.getText();
        String antirequisites = antirequisitesField.getText();
        String courseDescString = courseDesc.getText();
        ArrayList<String> courses = new ArrayList<>();

        if (!prerequisites.equals("")) {
            courses.addAll(new ArrayList<>(Arrays.asList(prerequisites.split(","))));
        }
        if (!antirequisites.equals("")) {
            courses.addAll(new ArrayList<>(Arrays.asList(antirequisites.split(","))));
        }
        if (!validateCourses(courseID, courseNumNameLabel.getText(), courses, true)) {
            return false;
        }

        ModifyCourseData.updateCourse(courseID, prerequisites, antirequisites, courseDescString);

        return true;
    }

    /**
     * Add handlers for the buttons
     */
    @Override
    public void addActions() {
        finishBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (editCourse()) {
                    CourseWindow courseWindow = new CourseWindow();
                    openWindow(courseWindow, finishBtn);
                } else {

                }
            }
        });
    }
}
