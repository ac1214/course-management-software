package coursesoftware;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ConsoleWindow extends BaseWindow {
    Image bookLogo = new Image(LoginWindow.class.getResourceAsStream("/icons/open-book.png"));
    ImageView imvBook = new ImageView(bookLogo);
    Image adminLogo = new Image(LoginWindow.class.getResourceAsStream("/icons/admin.png"));
    ImageView imvAdmin = new ImageView(adminLogo);
    Image progLogo = new Image(LoginWindow.class.getResourceAsStream("/icons/scroll.png"));
    ImageView imvProg = new ImageView(progLogo);
    Image deptLogo = new Image(LoginWindow.class.getResourceAsStream("/icons/university.png"));
    ImageView imvDept = new ImageView(deptLogo);
    Image logoutLogo = new Image(LoginWindow.class.getResourceAsStream("/icons/logout.png"));
    ImageView imvLogout = new ImageView(logoutLogo);

    private Button logoutButtonBtn = new Button("Logout", imvLogout);
    private Button viewEditProgramsBtn = new Button("View/Edit Programs", imvProg);
    private Button viewEditDepartmentBtn = new Button("View/Edit Departments", imvDept);
    private Button editAdminBtn = new Button("Edit Administrators", imvAdmin);
    private Button viewEditCourseBtn = new Button("View/Edit Courses", imvBook);

    /**
     * Constructor that will initialize the properties of the page
     */
    public ConsoleWindow() {
        this.windowTitle = "Admin Console";

        this.grid = new GridPane();
        initPage(grid);
    }

    /**
     * Initializes elements that will be on the GridPane
     *
     * @param grid GridPane object to add to
     */
    private void initPage(GridPane grid) {
        viewEditProgramsBtn.setMaxWidth(Double.MAX_VALUE);
        viewEditDepartmentBtn.setMaxWidth(Double.MAX_VALUE);
        viewEditCourseBtn.setMaxWidth(Double.MAX_VALUE);
        editAdminBtn.setMaxWidth(Double.MAX_VALUE);
        logoutButtonBtn.setMaxWidth(Double.MAX_VALUE);
        logoutButtonBtn.setMaxHeight(Double.MAX_VALUE / 2);

        ImageView viewer = new ImageView();
        viewer.setFitWidth(425);
        viewer.setFitHeight(240);
        Image logo = new Image(LoginWindow.class.getResourceAsStream("/UofCLogo.png"));
        viewer.setImage(logo);
        grid.add(viewer, 0, 0);

        GridPane subgrid = new GridPane();

        subgrid.add(viewEditCourseBtn, 0, 0);
        subgrid.add(viewEditDepartmentBtn, 0, 1);
        subgrid.add(viewEditProgramsBtn, 1, 0);
        subgrid.add(editAdminBtn, 1, 1);
        grid.add(subgrid, 0, 1);
        grid.add(logoutButtonBtn, 0, 2);

        grid.setAlignment(Pos.CENTER);

    }

    /**
     * This method will handle inputs in the username/password fields and also
     * handle when the user clicks the "login" button, when the user presses enter
     * when in the username or password field
     */
    @Override
    public void addActions() {
        logoutButtonBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Logout pressed");
                LoginWindow loginWindow = new LoginWindow();
                openWindow(loginWindow, logoutButtonBtn);
            }
        });

        viewEditProgramsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ProgramWindow programWindow = new ProgramWindow();
                openWindow(programWindow, viewEditProgramsBtn);
            }
        });

        viewEditDepartmentBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DepartmentWindow departmentWindow = new DepartmentWindow();
                openWindow(departmentWindow, viewEditDepartmentBtn);
            }
        });

        viewEditCourseBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CourseWindow courseWindow = new CourseWindow();
                openWindow(courseWindow, viewEditCourseBtn);
            }
        });

        editAdminBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EditAdminWindow editAdminWindow = new EditAdminWindow();
                openWindow(editAdminWindow, editAdminBtn);
            }
        });
    }
}
