package coursesoftware;

import coursesoftware.database.DataModify;
import coursesoftware.windows.AlertWindow;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class BaseCourseWindow extends BaseWindow {
    protected Label windowLabel = new Label("");
    protected Label courseIDLabel = new Label("Course ID: ");
    protected Label departmentLabel = new Label("Department: ");
    protected Label courseNumLabel = new Label("Course Number: ");
    protected Label courseNameLabel = new Label("Course Name: ");
    protected Label prerequisitesLabel = new Label("Prerequisites: ");
    protected Label antirequisitesLabel = new Label("Antirequisites: ");
    protected Label courseDescriptionLabel = new Label("Course Description: ");

    /**
     * Constructor for base course window. Sets all buttons on screen and adds style
     * sheets
     */
    public BaseCourseWindow() {
        this.grid = new GridPane();

        this.grid.add(windowLabel, 0, 0);
        this.grid.add(courseIDLabel, 0, 1);
        this.grid.add(departmentLabel, 0, 2);
        this.grid.add(courseNumLabel, 0, 3);
        this.grid.add(courseNameLabel, 0, 4);
        this.grid.add(prerequisitesLabel, 0, 5);
        this.grid.add(antirequisitesLabel, 0, 6);
        this.grid.add(courseDescriptionLabel, 0, 7);

        this.windowHeight = 500;
        this.windowWidth = 500;
    }

    /**
     * validateCourses method makes sure you don't add two courses with the same
     * name
     *
     * @param courseID       course ID to compare
     * @param courseNum      course number to compare
     * @param coursesToCheck an array list of the courses that need to be compared
     * @param editCourse     check if the course is editable
     * @return boolean
     */
    protected boolean validateCourses(String courseID, String courseNum, ArrayList<String> coursesToCheck,
                                      boolean editCourse) {
        String alertTitle = "";
        if (editCourse) {
            alertTitle = "Could not edit course";
        } else {
            alertTitle = "Could not add course";
        }
        try {
            Integer.parseInt(courseNum);
        } catch (NumberFormatException e) {
            AlertWindow.displayErrorWindowWithMessage(alertTitle, "Make sure that the Course Number is a valid number", "Please enter a new Course Number and try again");
            return false;
        }


        if (DataModify.checkCourseExists(courseID) && !editCourse) {
            AlertWindow.displayErrorWindowWithMessage(alertTitle, "Make sure that the Course ID is unique", "Please enter a new Course ID and try again");
            return false;
        }

        // Make sure that the requisites are valid
        if (coursesToCheck.isEmpty()) {
            return true;
        } else {
            for (String s : coursesToCheck) {
                System.out.println("Course: \"" + s + "\"");
                if (!DataModify.checkCourseExists(s)) {
                    AlertWindow.displayErrorWindowWithMessage(alertTitle, "Make sure that the pre/anitrequisites are valid courses", "Please enter pre/anitrequisites seperated by commas and try again");
                    return false;
                }
            }

            return true;
        }
    }
}