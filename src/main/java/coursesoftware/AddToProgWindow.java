package coursesoftware;

import coursesoftware.database.ModifyProgramData;
import coursesoftware.windows.AlertWindow;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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
        ObservableList<String> list = ModifyProgramData.getProgramCourses(thisProg);
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
                    if (AlertWindow.displayConfirmationWindow("Adding courses", "Would you like to add the selcted courses to\nthe " + thisProg + " program?")) {
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
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size() - 1; i++) {
            sb.append(list.get(i));
            sb.append(',');
        }

        sb.append(list.get(list.size() - 1));

        ModifyProgramData.updateProgram(thisProg, sb.toString());

        initTable();
    }
}
