package coursesoftware;

import coursesoftware.database.DataModify;
import coursesoftware.windows.AlertWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class AddProgWindow extends BaseWindow {
    Button finishBtn = new Button("Finish");
    TextField progField = new TextField();

    /**
     * Set window parameters. Initialize scene with grid elements
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
                    if (AlertWindow.displayConfirmationWindowWithMessage("Blank Field", "Incomplete Form", "Program field\ncan not be left blank \nThis Program will not be saved")) {
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
        DataModify.insertNewProgram(progID, "");
    }
}
