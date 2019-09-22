package coursesoftware;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class EditDeptWindow extends BaseWindow {
	Button finishBtn = new Button("Finish");
	Button removeBtn = new Button("Remove Program");
	Button addBtn = new Button("Add Program");

	private TableView<Program> currentDepartmentTable = new TableView<>();
	private String thisDept;

	/**
	 * Constructor that will initialize the properties of the page
	 */
	public EditDeptWindow(String deptName) {
		thisDept = deptName;
		this.windowHeight = 500;
		this.windowWidth = 500;
		this.windowTitle = "Edit Department";

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
		currentDepartmentTable.setMinWidth(400);

		grid.add(currentDepartmentTable, 0, 0);

		GridPane subGrid = new GridPane();
		subGrid.setHgap(10);
		subGrid.setVgap(10);

		double width = (410 - 20) / 3;
		addBtn.setMinWidth(width);
		removeBtn.setMinWidth(width);
		finishBtn.setMinWidth(width);

		subGrid.add(addBtn, 0, 0);
		subGrid.add(removeBtn, 1, 0);
		subGrid.add(finishBtn, 2, 0);
		subGrid.setAlignment(Pos.CENTER);

		grid.add(subGrid, 0, 1);
		grid.setAlignment(Pos.CENTER);
	}

	/**
	 * Initializes table in the JavaFX window
	 */
	private void initTable() {
		currentDepartmentTable.getColumns().clear();

		TableColumn<Program, String> column = new TableColumn<>("Program");
		column.setCellValueFactory(new PropertyValueFactory<>("progID"));
		column.setMinWidth(405);
		currentDepartmentTable.getColumns().add(column);

		ObservableList<Program> list = FXCollections.observableArrayList();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(DEPARTMENTDIR+ thisDept + ".txt")));

			String line;
			while ((line = br.readLine()) != null) {
				Program progObj = new Program(line);
				list.add(progObj);
			}
			br.close();
		} catch (Exception e) {
			// File not found
		}

		currentDepartmentTable.setItems(list);
	}

	/**
	 * Add handlers for the buttons
	 */
	@Override
	public void addActions() {
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DepartmentWindow departmentWindow = new DepartmentWindow();

				openWindow(departmentWindow, finishBtn);
			}
		});

		removeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				removeProgPrompt();
			}
		});

		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addProg();
			}
		});
	}

	/**
	 * Adds a program to a file
	 */
	private void addProg() {
		TextInputDialog addProgramPrompt = new TextInputDialog();
		addProgramPrompt.setTitle("Add Program");
		addProgramPrompt.setHeaderText("Add a Program to " + thisDept);
		addProgramPrompt.setContentText("Input Program Name:");
		Optional<String> result = addProgramPrompt.showAndWait();
		if (result.isPresent()) {
			validateProg(result.get());
		}
	}

	private void addProgToFile(String programName) {

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(DEPARTMENTDIR + thisDept + ".txt", true));
			BufferedWriter bw2 = new BufferedWriter(new FileWriter(PROGRAMLIST, true));

			bw.write(programName + "\n");
			bw2.write(programName + "\n");
			bw.close();
			bw2.close();
		} catch (IOException e) {
			System.out.println("File can not be opened");
		}

		initTable();
	}

	/**
	 * This method will validate a program name and make sure that programs with
	 * duplicate names are not added to the file
	 * 
	 * @param newProgName program name that should be validated and then added to
	 *                    the file
	 */
	private void validateProg(String newProgName) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(PROGRAMLIST)));

			String line;
			while ((line = br.readLine()) != null) {
				if (line.equals(newProgName)) {
					br.close();
					// Display error message
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Could not add program");
					alert.setHeaderText("Make sure that the program name is unique");
					alert.setContentText("Please enter program name and try again");

					alert.showAndWait();
					return;
				}
			}
			br.close();
		} catch (Exception e) {
			// File not found
		}
		addProgToFile(newProgName);
	}

	/**
	 * This method will pop-up a window to verify if the selected program should be
	 * removed
	 */
	private void removeProgPrompt() {
		if (currentDepartmentTable.getSelectionModel().getSelectedItem() != null) {
			Program selectedProgram = currentDepartmentTable.getSelectionModel().getSelectedItem();
			String progID = selectedProgram.getProgID();
			Alert removeCourseAlert = new Alert(AlertType.CONFIRMATION);
			removeCourseAlert.setTitle("Remove Program");
			removeCourseAlert.setHeaderText("The department \"" + progID + "\" will be removed");
			removeCourseAlert.setContentText("Would you like to continue?");

			Optional<ButtonType> result = removeCourseAlert.showAndWait();

			if (result.get() == ButtonType.OK) {
				removeProg(progID);
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Select a program");
			alert.setContentText("Please select a program that\n you would like to remove");

			alert.showAndWait();
		}
	}

	/**
	 * This method will remove a specified program from the program file
	 * 
	 * @param progID The id of the program that is to be removed
	 */
	private void removeFromProgFile(String progID) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(PROGRAMLIST)));
			String line;
			ArrayList<String> addToFile = new ArrayList<>();
			while ((line = br.readLine()) != null) {
				if (!line.equals(progID)) {
					addToFile.add(line + "\n");
				}
			}
			br.close();
			fw = new FileWriter(PROGRAMLIST);
			bw = new BufferedWriter(fw);
			System.out.println(addToFile.size());

			for (String s : addToFile) {
				System.out.println(s);
				bw.write(s);
			}
			bw.close();
		} catch (Exception e) {
			System.out.println(e);
			// File not found
		}

		initTable();
	}

	/**
	 * This method will remove a program with a specified name
	 * 
	 * @param progID The name of the program that should be removed
	 */
	private void removeProg(String progID) {
		removeFromProgFile(progID);

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(DEPARTMENTDIR + thisDept + ".txt")));
			String line;
			ArrayList<String> addToFile = new ArrayList<>();

			while ((line = br.readLine()) != null) {
				if (!line.equals(progID)) {
					addToFile.add(line + "\n");
				}
			}
			br.close();

			BufferedWriter bw = new BufferedWriter(new FileWriter(DEPARTMENTDIR + thisDept + ".txt"));

			for (String s : addToFile) {
				System.out.println(s);
				bw.write(s);
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
			// File not found
		}

		initTable();
	}
}
