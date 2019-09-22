package coursesoftware;

import java.io.*;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;

public class AddToProgWindow extends BaseWindow {
	Button finishBtn = new Button("Finish");
	private ListView<String> courseListView = new ListView<String>();
	private String thisProg;

	/**
	 * Constructor that will initialize the properties of the page
	 */
	public AddToProgWindow(String progName) {
		thisProg = progName;
		this.windowHeight = 500;
		this.windowWidth = 500;
		this.windowTitle = "Add course to Program";

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
		courseListView.setMinWidth(400);

		grid.add(courseListView, 0, 0);

		grid.add(finishBtn, 0, 1);
		grid.setAlignment(Pos.CENTER);
	}

	/**
	 * Initializes the table for adding courses to programs
	 */
	private void initTable() {
		ObservableList<String> list = FXCollections.observableArrayList();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(COURSESFILE)));
			String line;
			while ((line = br.readLine()) != null) {
				String[] course = line.split(";");
				list.add(course[0]);
			}
			br.close();

			br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(PROGRAMDIR + thisProg + ".txt")));

			while ((line = br.readLine()) != null) {
				list.remove(line);
			}

			br.close();
		} catch (Exception e) {
			// File not found
		}

		courseListView.setItems(list);

		courseListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	/**
	 * Adds alerts and calls other methods when clicking elements
	 */
	@Override
	public void addActions() {
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (courseListView.getSelectionModel().isEmpty()) {
					EditProgWindow editProgWindow = new EditProgWindow(thisProg);
					openWindow(editProgWindow, finishBtn);
				} else {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Adding courses");
					alert.setHeaderText("Would you like to add the selcted courses to\nthe " + thisProg + " program?");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						addCoursesToFile();
					}
					EditProgWindow editProgWindow = new EditProgWindow(thisProg);
					openWindow(editProgWindow, finishBtn);
				}
			}
		});
	}

	/**
	 * Writes a course to a programs file to associate the course with a program
	 */
	private void addCoursesToFile() {
		ObservableList<String> list = courseListView.getSelectionModel().getSelectedItems();
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(PROGRAMDIR + thisProg + ".txt", true));

			for (String courseID : list) {
				bw.write(courseID + "\n");
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File can not be opened");
		}

		initTable();
	}
}
