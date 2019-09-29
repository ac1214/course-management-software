package coursesoftware;

import coursesoftware.database.DataModify;
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

public class StudentViewWindow extends BaseWindow {
	private TableView<Program> programTable = new TableView<>();
	private Button finishBtn = new Button("Log Out");
	private Button viewProgBtn = new Button("View Program");

	/**
	 * Constructor that will initialize the properties of the page
	 */
	public StudentViewWindow() {
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
		programTable.setEditable(false);

		TableColumn<Program, String> program = new TableColumn<Program, String>("Program Name");
		program.setCellValueFactory(new PropertyValueFactory<>("progID"));
		program.setMinWidth(405);
		programTable.getColumns().add(program);

		ObservableList<Program> list = DataModify.getPrograms();

		programTable.setItems(list);
	}

	/**
	 * Add handlers for the buttons
	 */
	@Override
	public void addActions() {
		viewProgBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (programTable.getSelectionModel().getSelectedItem() != null) {
					Program selectedProg = programTable.getSelectionModel().getSelectedItem();
					String progID = selectedProg.getProgID();
					ViewProgramWindow viewProgramWindow = new ViewProgramWindow(progID);
					openWindow(viewProgramWindow, viewProgBtn);
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Select a Program");
					alert.setContentText("Please select a Program that\nyou would like to view");

					alert.showAndWait();
				}
			}
		});

		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LoginWindow loginWindow = new LoginWindow();
				openWindow(loginWindow, finishBtn);
			}
		});
	}

	/**
	 * Initializes elements that will be on the GridPane
	 * 
	 * @param grid GridPane object to add to
	 */
	private void initPage(GridPane grid) {
		finishBtn.setMinWidth(205);
		viewProgBtn.setMinWidth(205);

		programTable.setMinWidth(400);

		GridPane subGrid = new GridPane();

		subGrid.setHgap(10);
		subGrid.setVgap(10);
		subGrid.add(viewProgBtn, 0, 0);
		subGrid.add(finishBtn, 1, 0);
		GridPane.setHalignment(finishBtn, HPos.RIGHT);

		grid.add(programTable, 0, 0);
		grid.add(subGrid, 0, 1);
		grid.setAlignment(Pos.CENTER);
	}
}
