package coursesoftware;

import java.util.Arrays;

import coursesoftware.database.DataModify;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import coursesoftware.datatypes.Course;

public class ViewCourseWindow extends BaseCourseWindow {
	private Button finishBtn = new Button("Finish");
	private String courseID = "";
	private Label courseIDNameLabel = new Label("");
	private Label departmentNameLabel = new Label("");
	private Label courseNumNameLabel = new Label("");
	private Label courseNameLabel = new Label("");
	private Label prerequisitesField = new Label("");
	private Label antirequisitesField = new Label("");
	private TextArea courseDesc = new TextArea();
	private String parentProg = "";

	/**
	 * ViewCourseWindow constructor to initialize the view course window in JavaFX
	 * 
	 * @param courseID   ID of the course to view
	 * @param parentProg Program that this course has come from
	 */
	public ViewCourseWindow(String courseID, String parentProg) {
		super();
		this.windowTitle = "View Course";
		this.windowLabel.setText("View Course");
		this.courseID = courseID;
		this.parentProg = parentProg;
		setFields();

		courseDesc.setMaxWidth(200);
		courseDesc.setEditable(false);

		grid.add(courseIDNameLabel, 1, 1);
		grid.add(departmentNameLabel, 1, 2);
		grid.add(courseNumNameLabel, 1, 3);
		grid.add(courseNameLabel, 1, 4);
		grid.add(prerequisitesField, 1, 5);
		grid.add(antirequisitesField, 1, 6);
		grid.add(courseDesc, 1, 7);

		grid.add(finishBtn, 0, 8);

		grid.setAlignment(Pos.CENTER);
	}

	/**
	 * This method loads the fields with the values in the course file.
	 */
	public void setFields() {
		Course editingCourse = null;

		editingCourse = DataModify.getCourse(courseID);
		String courseDescString = String.join("\n", Arrays.asList(editingCourse.getDescription().split("NEWLINE")));
		courseDescString = courseDescString.equals("NONE") ? "" : courseDescString;
		courseDesc.setText(courseDescString);
		courseDesc.setWrapText(true);
		courseDesc.setDisable(true);
		courseDesc.setStyle("-fx-opacity: 1.0; -fx-text-inner-color: black;");


		courseIDNameLabel.setText(editingCourse.getCourseID());
		departmentNameLabel.setText(editingCourse.getCourseDep());
		courseNumNameLabel.setText(editingCourse.getCourseNum());
		courseNameLabel.setText(editingCourse.getCourseName());
		prerequisitesField.setText(editingCourse.getPrerequisites());
		antirequisitesField.setText(editingCourse.getAntirequisites());
	}

	/**
	 * Add handlers for the buttons
	 */
	@Override
	public void addActions() {
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ViewProgramWindow viewProgramWindow = new ViewProgramWindow(parentProg);
				openWindow(viewProgramWindow, finishBtn);
			}
		});
	}
}
