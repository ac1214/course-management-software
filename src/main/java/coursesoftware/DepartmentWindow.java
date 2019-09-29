package coursesoftware;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;
import coursesoftware.database.DataModify;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import coursesoftware.datatypes.Department;

public class DepartmentWindow extends BaseWindow {
	private TableView<Department> departmentTable = new TableView<>();
	private Button finishBtn = new Button("Finish");
	private Button addDeptBtn = new Button("Add New Department");
	private Button editDeptBtn = new Button("Edit Department");
	private Button removeDeptBtn = new Button("Remove Department");

	/**
	 * Constructor that will initialize the properties of the page
	 */
	public DepartmentWindow() {
		this.windowHeight = 500;
		this.windowWidth = 500;
		this.windowTitle = "Departments";

		this.grid = new GridPane();
		initTable();
		initPage(grid);
	}

	/**
	 * Initializes table in the JavaFX window
	 */
	private void initTable() {
		departmentTable.getColumns().clear();
		departmentTable.setEditable(true);

		TableColumn<Department, String> department = new TableColumn<Department, String>("Department Name");
		department.setCellValueFactory(new PropertyValueFactory<>("deptID"));
		department.setMinWidth(415);
		departmentTable.getColumns().add(department);

		ObservableList<Department> list = DataModify.getDepartments();

		departmentTable.setItems(list);
	}

	/**
	 * Add handlers for the buttons
	 */
	@Override
	public void addActions() {
		addDeptBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addDeptPrompt();
			}
		});

		editDeptBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (departmentTable.getSelectionModel().getSelectedItem() != null) {
					Department selectedDept = departmentTable.getSelectionModel().getSelectedItem();
					String deptID = selectedDept.getDeptID();
					EditDeptWindow editDeptWindow = new EditDeptWindow(deptID);
					openWindow(editDeptWindow, editDeptBtn);
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Select a Deparment");
					alert.setContentText("Please select a department that\nyou would like to edit");

					alert.showAndWait();
				}
			}
		});

		removeDeptBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				removeDeptPrompt();
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
	 * This method will pop-up a prompt to take an input to add a department
	 */
	private void addDeptPrompt() {
		TextInputDialog deptPrompt = new TextInputDialog();
		deptPrompt.setTitle("Add a department");
		deptPrompt.setHeaderText("Add a new department");
		deptPrompt.setContentText("Enter department name:");

		Optional<String> result = deptPrompt.showAndWait();

		if (result.isPresent()) {
			if (!result.get().equals("")) {
				validateDept(result.get());
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Could not add department");
				alert.setHeaderText("Incomplete Form");
				alert.setContentText("Department field can not be blank\nPlease enter department name and try again");

				alert.showAndWait();
			}
		}
	}

	/**
	 * This method will validate a department name before adding it to the file to
	 * make sure that duplicate department names will not exist.
	 * 
	 * @param newDeptName
	 */
	private void validateDept(String newDeptName) {
		DataModify.checkDepartmentExists(newDeptName);

		if (DataModify.checkDepartmentExists(newDeptName)) {
			// Display error message
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Could not add department");
			alert.setHeaderText("Make sure that the department name is unique");
			alert.setContentText("Please enter department name and try again");

			alert.showAndWait();
			return;
		}

		addDepartment(newDeptName);
	}

	private void addDepartment(String deptID) {
		DataModify.insertNewDepartment(deptID);
		initTable();
	}

	/**
	 * This method will prompt a popup window to ask the user to confirm if they
	 * would like a selected course to be deleted.
	 */
	private void removeDeptPrompt() {
		if (departmentTable.getSelectionModel().getSelectedItem() != null) {
			Department selectedDept = departmentTable.getSelectionModel().getSelectedItem();
			String deptID = selectedDept.getDeptID();
			Alert removeCourseAlert = new Alert(AlertType.CONFIRMATION);
			removeCourseAlert.setTitle("Remove Department");
			removeCourseAlert.setHeaderText("The department \"" + deptID + "\" will be removed");
			removeCourseAlert.setContentText("Would you like to continue?");

			Optional<ButtonType> result = removeCourseAlert.showAndWait();

			if (result.get() == ButtonType.OK) {
				removeDept(deptID);
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Select a Department");
			alert.setContentText("Please select a department that\nyou would like to remove");

			alert.showAndWait();
		}
	}

	/**
	 * This method will remove the department with the specified name from the database
	 * 
	 * @param deptID name of department to remove
	 */
	private void removeDept(String deptID) {
		DataModify.removeDepartment(deptID);
		initTable();
	}

	/**
	 * Initializes elements that will be on the GridPane
	 * 
	 * @param grid GridPane object to add to
	 */
	private void initPage(GridPane grid) {
		addDeptBtn.setMinWidth(205);
		editDeptBtn.setMinWidth(205);
		removeDeptBtn.setMinWidth(205);
		finishBtn.setMinWidth(205);

		departmentTable.setMinWidth(400);

		GridPane subGrid = new GridPane();

		subGrid.setHgap(10);
		subGrid.setVgap(10);
		subGrid.add(addDeptBtn, 0, 0);
		subGrid.add(editDeptBtn, 1, 0);
		subGrid.add(removeDeptBtn, 0, 1);
		subGrid.add(finishBtn, 1, 1);
		GridPane.setHalignment(finishBtn, HPos.RIGHT);

		grid.add(departmentTable, 0, 0);
		grid.add(subGrid, 0, 1);
		grid.setAlignment(Pos.CENTER);
	}
}
