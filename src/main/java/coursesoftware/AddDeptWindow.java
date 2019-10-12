package coursesoftware;

import coursesoftware.database.DataModify;
import coursesoftware.windows.AlertWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class AddDeptWindow extends BaseWindow {
    Button finishBtn = new Button("Finish");
    TextField deptField = new TextField();

    /**
     * Set window parameters. Initialize scene with grid elements
     */
    public AddDeptWindow() {
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
                        AlertWindow.displayErrorWindowWithMessage("Could not add department", "Make sure that the department name is unique", "Please enter department name and try again");
                    }
                } else {
                    if (AlertWindow.displayConfirmationWindowWithMessage("Blank Field", "Incomplete Form", "Department field\ncan not be left blank \nThis department will not be saved")) {
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
        return DataModify.checkDepartmentExists(deptField.getText());
    }

    /**
     * Method adds a department to the department list.
     */
    private void addDepartment() {
        String deptID = deptField.getText();
        DataModify.insertNewDepartment(deptID);
    }
}
