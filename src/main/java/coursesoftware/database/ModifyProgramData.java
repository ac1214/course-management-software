package coursesoftware.database;

import coursesoftware.datatypes.Program;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModifyProgramData extends DataModify {
    public static boolean insertNewProgram(String programName, String departmentName) {
        try {
            databaseHandler.executeUpdate("INSERT INTO PROGRAMS (program_name, department_id) VALUES (\'" + programName + "\', \'" + departmentName + "\');");
            return true;
        } catch (SQLException ex) {
            System.out.println("Could not insert program " + programName);
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean checkProgramExists(String programName) {
        return checkElementExists("PROGRAMS", "program_name", programName);
    }

    public static boolean removeProgram(String programName) {
        return removeFromTable("PROGRAMS", "program_name", programName);
    }

    public static boolean updateProgram(String programName, String courses) {
        try {
            databaseHandler.executeUpdate("UPDATE PROGRAMS SET courses = \'" + courses + "\' WHERE program_name = \'" + programName + "\';");
            return true;
        } catch (SQLException ex) {
            System.out.println("Failed while updating program: " + programName);
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Get a list of  programs
     *
     * @return a list of programs
     */
    public static ObservableList<Program> getPrograms() {
        ResultSet rs = null;
        ObservableList<Program> programList = FXCollections.observableArrayList();

        try {
            rs = databaseHandler.executeQuery("SELECT * FROM PROGRAMS;");

            while (rs.next()) {
                programList.add(new Program(rs.getString(3)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return programList;
    }

    /**
     * Get a list of  programs for a specific department
     *
     * @return a list of programs
     */
    public static ObservableList<Program> getPrograms(String department) {
        ResultSet rs = null;
        ObservableList<Program> programList = FXCollections.observableArrayList();

        try {
            rs = databaseHandler.executeQuery("SELECT * FROM PROGRAMS WHERE department_id = \'" + department + "\';");

            while (rs.next()) {
                programList.add(new Program(rs.getString(3)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return programList;
    }

    public static ObservableList<String> getProgramCourses(String programName) {
        ResultSet rs = null;
        ObservableList<String> courseList = FXCollections.observableArrayList();

        try {
            rs = databaseHandler.executeQuery("SELECT * FROM PROGRAMS WHERE program_name = \'" + programName + "\';");

            while (rs.next()) {
                String coursesString = rs.getString(4);
                if (coursesString != null) {
                    String[] courses = rs.getString(4).split(",");
                    for (String course : courses)
                        courseList.add(course);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return courseList;
    }
}
