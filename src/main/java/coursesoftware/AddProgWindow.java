package coursesoftware;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

public class AddProgWindow extends BaseWindow {
	Button finishBtn = new Button("Finish");
	TextField progField = new TextField();

	/**
	 * Set window parameters. Initialize scene with grid elements
	 * 
	 */
	public AddProgWindow() {
		this.windowHeight = 500;
		this.windowWidth = 500;
		this.windowTitle = "Add Program";

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

		Label progLabel = new Label("Program:");
		grid.add(progLabel, 0, 1);
		grid.add(progField, 1, 1);
		grid.add(finishBtn, 0, 2);
	}

	/**
	 * Add functionality and alerts to clickable elements
	 */
	@Override
	public void addActions() {
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!progField.getText().equals("")) {
					addProgram();
					ProgramWindow progWindow = new ProgramWindow();
					openWindow(progWindow, finishBtn);
				} else {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Blank Field");
					alert.setHeaderText("Incomplete Form");
					alert.setContentText("Program field\ncan not be left blank \nThis Program will not be saved");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						ProgramWindow progWindow = new ProgramWindow();
						openWindow(progWindow, finishBtn);
					}
				}
			}
		});
	}

	/**
	 * Write a program in the program field to the program list
	 */
	private void addProgram() {
		String progID = progField.getText();
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(PROGRAMLIST, true));

			bw.write(progID + "\n");
			bw.close();
		} catch (IOException e) {
			System.out.println("File can not be opened");
		}
	}
}
