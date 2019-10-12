package coursesoftware.database;

import coursesoftware.datatypes.Department;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ModifyDepartmentData extends DataModify {
    public static boolean insertNewDepartment(String department) {
        return insertToTable("DEPARTMENTS", new String[]{department});
    }

    public static boolean checkDepartmentExists(String departmentName) {
        return checkElementExists("DEPARTMENTS", "NAME", departmentName);
    }

    public static boolean removeDepartment(String department) {
        return removeFromTable("DEPARTMENTS", "NAME", department);
    }

    /**
     * Get a list of the departments
     *
     * @return a list of departments
     */
    public static ObservableList<Department> getDepartments() {
        ResultSet rs = null;
        ObservableList<Department> departmentList = FXCollections.observableArrayList();

        try {
            rs = databaseHandler.executeQuery("SELECT * FROM DEPARTMENTS;");

            while (rs.next()) {
                departmentList.add(new Department(rs.getString(1)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return departmentList;
    }

    /**
     * Get a list of the departments
     *
     * @return a list of departments
     */
    public static ArrayList<String> getDepartmentNameList() {
        ResultSet rs = null;
        ArrayList<String> departmentList = new ArrayList<>();

        try {
            rs = databaseHandler.executeQuery("SELECT * FROM DEPARTMENTS;");

            while (rs.next()) {
                departmentList.add(rs.getString(1));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return departmentList;
    }
}
