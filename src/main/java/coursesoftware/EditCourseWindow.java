package coursesoftware;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditCourseWindow extends BaseCourseWindow {
	private Button finishBtn = new Button("Finish");
	private String courseID = "";
	private Label courseIDNameLabel = new Label("");
	private Label departmentNameLabel = new Label("");
	private Label courseNumNameLabel = new Label("");
	private Label courseNameLabel = new Label("");
	private TextField prerequisitesField = new TextField();
	private TextField antirequisitesField = new TextField();
	TextArea courseDesc = new TextArea();

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
		Course editingCourse = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(COURSESFILE)));

			String line;
			while ((line = br.readLine()) != null) {
				String[] course = line.split(";");
				if (course[0].equals(courseID)) {
					editingCourse = new Course(course);
					String courseDescString = course[6].equals("NONE") ? ""
							: String.join("\n", Arrays.asList(course[6].split("NEWLINE")));
					courseDesc.setText(courseDescString);
					break;
				}
			}
			br.close();
		} catch (Exception e) {
			// File not found
		}
		courseIDNameLabel.setText(editingCourse.getCourseID());
		departmentNameLabel.setText(editingCourse.getCourseDep());
		courseNumNameLabel.setText(editingCourse.getCourseNum());
		courseNameLabel.setText(editingCourse.getCourseName());
		prerequisitesField.setText(editingCourse.getPrerequisites());
		antirequisitesField.setText(editingCourse.getAntirequisites());
	}

	/**
	 * This method will save an edited course and will update the course file with
	 * the new information
	 * 
	 * @return Returns whether the course was able to successfully update the file
	 */
	public boolean editCourse() {
		String prerequisites = prerequisitesField.getText().equals("") ? "NONE" : prerequisitesField.getText();
		String antirequisites = antirequisitesField.getText().equals("") ? "NONE" : antirequisitesField.getText();
		String courseDescString = String.join("NEWLINE", Arrays.asList(courseDesc.getText().split("\n")));
		courseDescString = courseDescString.equals("") ? "NONE" : courseDescString;
		ArrayList<String> courses = new ArrayList<>();

		if (!prerequisites.equals("NONE")) {
			courses.addAll(new ArrayList<>(Arrays.asList(prerequisites.split(","))));
		}
		if (!antirequisites.equals("NONE")) {
			courses.addAll(new ArrayList<>(Arrays.asList(antirequisites.split(","))));
		}
		if (validateCourses(courseID, courseNumNameLabel.getText(), courses, true)) {
		} else {
			return false;
		}

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(COURSESFILE)));
			String line;
			ArrayList<String> addToFile = new ArrayList<>();

			while ((line = br.readLine()) != null) {
				String[] course = line.split(";");
				System.out.println(Arrays.toString(course));
				if (course[0].equals(courseID)) {
					addToFile.add(course[0] + ";" + course[1] + ";" + course[2] + ";" + course[3] + ";" + prerequisites
							+ ";" + antirequisites + ";" + courseDescString + "\n");
				} else {
					addToFile.add(String.join(";", course) + "\n");
				}
			}
			br.close();

			BufferedWriter bw = new BufferedWriter(new FileWriter(COURSESFILE));

			for (String s : addToFile) {
				bw.write(s);
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
			// File not found
		}
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
