package coursesoftware.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import coursesoftware.datatypes.Admin;
import coursesoftware.datatypes.Course;
import coursesoftware.datatypes.Department;
import coursesoftware.datatypes.Program;
import coursesoftware.validation.Password;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataModify {
    private static DatabaseHandler databaseHandler = null;

    static {
        databaseHandler = new DatabaseHandler();
    }

    public static boolean insertNewCourse(Course course) {

        return false;
    }

    public static boolean insertNewDepartment(Department department) {

        return false;
    }

    public static boolean insertNewProgram(Program program) {
        return false;
    }

    public static boolean checkCourseExists(String courseName) {
        return false;
    }

    public static boolean checkDepartmentExists(String departmentName) {
        return false;
    }

    public static boolean checkProgramExists(String programName) {
        return false;
    }

    public static boolean removeCourse(Course course) {
        return false;
    }

    public static boolean removeDepartment(Department department) {
        return false;
    }

    public static boolean removeProgram(Program program) {
        return false;
    }

    /**
     * Get a list of the admin users
     * @return a list of admin users
     */
    public static ObservableList<Admin> getAdminUsers() {
        ResultSet rs = null;
        ObservableList<Admin> adminUserList = FXCollections.observableArrayList();

        try {
            rs = databaseHandler.executeQuery("SELECT * FROM USERS WHERE isadmin = true;");

            while (rs.next()) {
                adminUserList.add(new Admin(rs.getString(1)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return adminUserList;
    }

    /**
     * This method will validate a login attempt with the existing login ids
     * @param username username that was input into the input field
     * @param password password that was input into the password field
     * @return 1 if admin, 2 if student, -1 if invalid login
     */
    public static int validateUser(String username, String password) {
        String salt = null;
        String hash = null;
        boolean isAdmin = false;

        ResultSet rs = null;
        try {
            rs = databaseHandler.executeQuery("SELECT * FROM USERS WHERE id = \'" + username + "\';");

            while (rs.next()) {
                isAdmin = rs.getBoolean(2);
                salt = rs.getString(3);
                hash = rs.getString(4);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Check if user was in the database
        if(salt == null || hash == null) {
            return -1;
        }

        String toCheckHash = Password.getHash(password, salt);

        // Check if the stored hash is equal to the input password's hash
        if(hash.equals(toCheckHash)) {
            return isAdmin ? 1 : 2;
        } else {
            return -1;
        }
    }

    /**
     * This method will add a new user
     * @param username username that was input into the input field
     * @param password password that was input into the password field
     * @return boolean value of whether user was successfully added to database
     */
    public static boolean addUser(String username, String password, boolean isAdmin) {
        String salt = Password.getNextSalt();
        String hash = Password.getHash(password, salt);

        try {
            databaseHandler.executeUpdate("INSERT INTO USERS VALUES (\'" + username + "\', " + isAdmin + " ,\'" + salt + "\', \'" + hash + "\');");
            return true;
        } catch (SQLException ex) {
            System.out.println("Could not add user " + username);
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * This method will remove the user with the specified username
     * @param username user to remove
     */
    public static boolean removeUser(String username) {
        try {
            databaseHandler.executeUpdate("DELETE FROM USERS WHERE id = \'" + username + "\';");
            return true;
        }  catch (SQLException ex) {
            System.out.println("Could not remove user " + username);
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * This method will check if the specified user exists
     * @param username user to remove
     */
    public static boolean userExists(String username) {
        int count = 0;
        ResultSet rs = null;

        try {
            rs = databaseHandler.executeQuery("SELECT * FROM USERS WHERE id = \'" + username + "\';");

            while (rs.next()) {
                count++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return count > 0;
    }


    /**
     * This method will update a user with a new password
     * @param username username for password change
     * @param newPassword password to change to
     */
    public static boolean changePassword(String username, String newPassword) {
        try {
            String salt = Password.getNextSalt();
            String hash = Password.getHash(newPassword, salt);

            databaseHandler.executeUpdate("UPDATE USERS SET salt = \'" + salt + "\'," + "passwordhash = \'" + hash + "\' WHERE id = \'" + username + "\';");
            return true;
        }  catch (SQLException ex) {
            System.out.println("Update password for " + username);
            ex.printStackTrace();
            return false;
        }
    }
}
