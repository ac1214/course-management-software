package coursesoftware;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import coursesoftware.database.DataModify;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import coursesoftware.datatypes.Program;

public class ProgramWindow extends BaseWindow {
	private TableView<Program> programTable = new TableView<>();
	private Button finishBtn = new Button("Finish");
	private Button editProgBtn = new Button("Edit Program");

	/**
	 * Constructor that will initialize the properties of the page
	 */
	public ProgramWindow() {
		this.windowHeight = 500;
		this.windowWidth = 500;
		this.windowTitle = "Programs";

		this.grid = new GridPane();
		initTable();
		initPage(grid);
	}

	/**
	 * Initializes table in the JavaFX window
	 */
	private void initTable() {
		programTable.getColumns().clear();
		programTable.setEditable(true);

		TableColumn<Program, String> program = new TableColumn<Program, String>("Program Name");
		program.setCellValueFactory(new PropertyValueFactory<>("progID"));
		program.setMinWidth(400);
		programTable.getColumns().add(program);

		ObservableList<Program> list = DataModify.getPrograms();


		programTable.setItems(list);
	}

	/**
	 * Add handlers for the buttons
	 */
	@Override
	public void addActions() {
		editProgBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (programTable.getSelectionModel().getSelectedItem() != null) {
					Program selectedProg = programTable.getSelectionModel().getSelectedItem();
					String progID = selectedProg.getProgID();
					EditProgWindow editProgWindow = new EditProgWindow(progID);
					openWindow(editProgWindow, editProgBtn);
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Select a Program");
					alert.setContentText("Please select a program that\nyou would like to edit");

					alert.showAndWait();
				}
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
	 * Initializes elements that will be on the GridPane
	 * 
	 * @param grid GridPane object to add to
	 */
	private void initPage(GridPane grid) {
		editProgBtn.setMinWidth(205);
		finishBtn.setMinWidth(205);

		programTable.setMinWidth(400);

		GridPane subGrid = new GridPane();

		subGrid.setHgap(10);
		subGrid.setVgap(10);
		subGrid.add(editProgBtn, 0, 0);
		subGrid.add(finishBtn, 1, 0);
		GridPane.setHalignment(finishBtn, HPos.RIGHT);

		grid.add(programTable, 0, 0);
		grid.add(subGrid, 0, 1);
		grid.setAlignment(Pos.CENTER);

	}
}
