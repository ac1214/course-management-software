package coursesoftware;

import java.io.*;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class AddDeptWindow extends BaseWindow {
	Button finishBtn = new Button("Finish");
	TextField deptField = new TextField();

	/**
	 * Set window parameters. Initialize scene with grid elements
	 * 
	 */
	public AddDeptWindow() {
		this.windowHeight = 500;
		this.windowWidth = 500;
		this.windowTitle = "Add department";

		this.grid = new GridPane();
		initPage(grid);
	}

	/**
	 * Initializes elements that will be on the GridPane
	 * 
	 * @param grid GridPane object to add to
	 */
	private void initPage(GridPane grid) {
		finishBtn.setMinWidth(205);

		Label deptLabel = new Label("Department:");
		grid.add(deptLabel, 0, 1);
		grid.add(deptField, 1, 1);
		grid.add(finishBtn, 1, 2);
	}

	/*
	 * Add actions adds functionality to buttons when clicked. Includes alerts and
	 * confirmation windows.
	 */
	@Override
	public void addActions() {
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!deptField.getText().equals("")) {
					if (notDuplicateDep()) {
						addDepartment();
						DepartmentWindow deptWindow = new DepartmentWindow();
						openWindow(deptWindow, finishBtn);
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Could not add department");
						alert.setHeaderText("Make sure that the department name is unique");
						alert.setContentText("Please enter department name and try again");

						alert.showAndWait();
					}
				} else {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Blank Field");
					alert.setHeaderText("Incomplete Form");
					alert.setContentText("Department field\ncan not be left blank \nThis department will not be saved");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						DepartmentWindow deptWindow = new DepartmentWindow();
						openWindow(deptWindow, finishBtn);
					}
				}
			}
		});
	}

	/**
	 * Method checks department list to make sure that the new department is not the
	 * same as one that exists.
	 * 
	 * @return boolean return true if not a duplicate department
	 */
	private boolean notDuplicateDep() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(DEPARTMENTLIST)));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.equals(deptField.getText())) {
					br.close();
					return false;
				}
			}
			br.close();
		} catch (Exception e) {
			// File not found
		}
		return true;
	}

	/**
	 * Method adds a department to the department list.
	 */
	private void addDepartment() {
		String deptID = deptField.getText();
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(DEPARTMENTLIST, true));

			bw.write(deptID + "\n");
			bw.close();
		} catch (IOException e) {
			System.out.println("File can not be opened");
		}
	}
}
