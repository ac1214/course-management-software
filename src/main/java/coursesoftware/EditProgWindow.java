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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class EditProgWindow extends BaseWindow {
	// Buttons for window
	Button finishBtn = new Button("Finish");
	Button removeBtn = new Button("Remove Course");
	Button addBtn = new Button("Add Course");

	private TableView<Program> currentProgramTable = new TableView<>();
	private String thisProg;

	/**
	 * Constructor that will initialize the properties of the page
	 */
	public EditProgWindow(String progName) {
		thisProg = progName;
		this.windowHeight = 500;
		this.windowWidth = 500;
		this.windowTitle = "Edit Programs";

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
		currentProgramTable.setMinWidth(400);

		grid.add(currentProgramTable, 0, 0);

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
		currentProgramTable.getColumns().clear();

		TableColumn<Program, String> column = new TableColumn<>("Course");
		column.setCellValueFactory(new PropertyValueFactory<>("progID"));
		column.setMinWidth(405);
		currentProgramTable.getColumns().add(column);

		ObservableList<Program> list = FXCollections.observableArrayList();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(PROGRAMDIR + thisProg + ".txt")));

			String line;
			while ((line = br.readLine()) != null) {
				Program progObj = new Program(line);
				System.out.println(progObj.getProgID());
				list.add(progObj);
			}
			br.close();
		} catch (Exception e) {
			// File not found
		}

		currentProgramTable.setItems(list);
	}

	/**
	 * Add handlers for the buttons
	 */
	@Override
	public void addActions() {
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ProgramWindow programWindow = new ProgramWindow();
				openWindow(programWindow, finishBtn);
			}
		});

		removeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				removeCoursePrompt();
			}
		});

		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AddToProgWindow addToProgWindow = new AddToProgWindow(thisProg);
				openWindow(addToProgWindow, addBtn);
			}
		});
	}

	/**
	 * Prompts the user when removing a course
	 */
	private void removeCoursePrompt() {
		if (currentProgramTable.getSelectionModel().getSelectedItem() != null) {
			Program selectedcourse = currentProgramTable.getSelectionModel().getSelectedItem();
			String courseID = selectedcourse.getProgID();
			Alert removeCourseAlert = new Alert(AlertType.CONFIRMATION);
			removeCourseAlert.setTitle("Remove Course");
			removeCourseAlert.setHeaderText("The course \"" + courseID + "\" will be removed");
			removeCourseAlert.setContentText("Would you like to continue?");

			Optional<ButtonType> result = removeCourseAlert.showAndWait();

			if (result.get() == ButtonType.OK) {
				removeCourse(courseID);
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Select a Course");
			alert.setContentText("Please select a Course that\n you would like to remove");

			alert.showAndWait();
		}
	}

	/**
	 * Removes course from the file
	 * 
	 * @param courseID ID of the course that should be removed
	 */
	private void removeCourse(String courseID) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(PROGRAMDIR + thisProg + ".txt")));
			String line;
			ArrayList<String> addToFile = new ArrayList<>();

			while ((line = br.readLine()) != null) {
				if (!line.equals(courseID)) {
					addToFile.add(line + "\n");
				}
			}
			br.close();

			BufferedWriter bw = new BufferedWriter(new FileWriter(PROGRAMDIR + thisProg + ".txt"));

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
