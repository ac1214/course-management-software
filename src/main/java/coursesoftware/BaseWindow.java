package coursesoftware;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public abstract class BaseWindow extends Application {
    protected String windowTitle;
    protected int windowWidth;
    protected int windowHeight;
    protected GridPane grid;

    /**
     * Start JavaFX window
     *
     * @param stage The stage shows the elements of the window
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle(windowTitle);
        stage.setWidth(1000);
        stage.setHeight(600);

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setAlignment(Pos.CENTER);

        addActions();

        stage.setScene(new Scene(grid));
        stage.show();
    }

    public void addActions() {
        System.out.println("No elements to implement");
    }

    public void setWindowTitle(String newWindowTitle) {
        this.windowTitle = newWindowTitle;
    }

    public void setWindowHeight(int newHeight) {
        this.windowHeight = newHeight;
    }

    public void setWindowWidth(int newWidth) {
        this.windowWidth = newWidth;
    }

    /**
     * This method will open up a window and close the current console window
     *
     * @param window        This is the window that should be opened
     * @param clickedButton Button that has been clicked
     */
    protected void openWindow(BaseWindow window, Button clickedButton) {
        Stage stage = (Stage) clickedButton.getScene().getWindow();
        window.start(stage);
        stage.show();
    }
}
