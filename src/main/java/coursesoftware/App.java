package coursesoftware;

import coursesoftware.database.DataModify;
import coursesoftware.validation.Password;
import javafx.application.Application;

public class App {
    public static void main(String[] args) {
        // Launch login window and start program
        Application.launch(LoginWindow.class, args);
        //PostgreSqlExample.main(new String[] {});
        //System.out.println(Password.getHash("admin", "fc19d224bc86968813ea62be7a4b7be8"));
    }
}
