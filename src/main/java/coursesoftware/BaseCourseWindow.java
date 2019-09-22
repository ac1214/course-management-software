package coursesoftware;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

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
		Alert alert = new Alert(AlertType.ERROR);
		if (editCourse) {
			alert.setTitle("Could not edit course");
		} else {
			alert.setTitle("Could not add course");
		}
		try {
			Integer.parseInt(courseNum);
		} catch (NumberFormatException e) {

			alert.setHeaderText("Make sure that the Course Number is a valid number");
			alert.setContentText("Please enter a new Course Number and try again");

			alert.showAndWait();
			return false;
		}

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(COURSESFILE)));

			String line;
			// Parsing the course file data to check if courses are the same.
			while ((line = br.readLine()) != null) {
				String[] course = line.split(";");
				coursesToCheck.remove(course[0]);
				if (course[0].equals(courseID) && !editCourse) {
					System.out.println(course[0]);
					br.close();
					alert.setHeaderText("Make sure that the Course ID is unique");
					alert.setContentText("Please enter a new Course ID and try again");

					alert.showAndWait();
					return false;
				}
			}
			br.close();
		} catch (Exception e) {
			System.out.println("File not found");
		}

		// Make sure that the requisites are valid
		if (coursesToCheck.isEmpty()) {
			return true;
		} else {
			for (String s : coursesToCheck) {
				System.out.println("Course: \"" + s + "\"");
			}
			alert.setHeaderText("Make sure that the pre/anitrequisites are valid courses");
			alert.setContentText("Please enter pre/anitrequisites seperated by commas and try again");

			alert.showAndWait();
			return false;
		}
	}
}