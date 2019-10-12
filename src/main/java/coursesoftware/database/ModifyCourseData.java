package coursesoftware.database;

import coursesoftware.datatypes.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModifyCourseData extends DataModify {
    public static boolean insertNewCourse(String courseID, String department, int courseNum, String courseName,
                                          String prerequisites, String antirequisites, String courseDescString) {

        if(!prerequisites.equals(""))
            addPrerequisites(courseID, prerequisites);
        if(!antirequisites.equals(""))
            addAntirequisites(courseID, antirequisites);


        try {
            databaseHandler.executeUpdate("INSERT INTO COURSES VALUES (\'" + courseID + "\', \'" + department + "\', " + courseNum + ", \'" + courseName + "\', \'" + courseDescString +"\');");

            return true;
        } catch (SQLException ex) {
            System.out.println("Could not insert course " + courseID);
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean insertNewCourse(Course course) {
        return insertNewCourse(course.getCourseID(), course.getCourseDep(), Integer.valueOf(course.getCourseNum()), course.getCourseName(), course.getPrerequisites(), course.getAntirequisites(), course.getDescription());
    }

    public static boolean addPrerequisites(String courseID, String prerequisites) {
        if(prerequisites.equals(""))
            return true;

        String[] prereqArr = prerequisites.split(",");
        try {
            for(int i = 0; i < prereqArr.length; i++) {
                databaseHandler.executeUpdate("INSERT INTO PREREQUISITES VALUES (\'" + courseID + "\', \'" + prereqArr[i] + "\');");
            }

            return true;
        }  catch (SQLException ex) {
            System.out.println("Failed while updating course: " + courseID);
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean addAntirequisites(String courseID, String antirequisites) {
        if(antirequisites.equals(""))
            return true;

        String[] antireqArr = antirequisites.split(",");
        try {
            for(int i = 0; i < antireqArr.length; i++) {
                databaseHandler.executeUpdate("INSERT INTO ANTIREQUISITES VALUES (\'" + courseID + "\', \'" + antireqArr[i] + "\');");
            }

            return true;
        }  catch (SQLException ex) {
            System.out.println("Failed while updating course: " + courseID);
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean checkCourseExists(String courseName) {
        return checkElementExists("COURSES", "COURSE_ID", courseName);
    }

    public static boolean removeCourse(String courseID) {
        return removeFromTable("COURSES", "course_id", courseID);
    }

    public static boolean updateCourse(String courseID,
                                       String prerequisites, String antirequisites, String courseDescString) {
        updatePrerequisites(courseID, prerequisites);
        updateAntirequisites(courseID, antirequisites);

        try {
            databaseHandler.executeUpdate("UPDATE COURSES SET description = \'" + courseDescString + "\' WHERE course_id = \'" + courseID + "\';");
            return true;
        } catch (SQLException ex) {
            System.out.println("Failed while updating course: " + courseID);
            ex.printStackTrace();
            return false;
        }
    }


    public static boolean updatePrerequisites(String courseID, String prerequisites) {
        removeFromTable("PREREQUISITES", "COURSE_ID", courseID);
        return addPrerequisites(courseID, prerequisites);
    }

    public static boolean updateAntirequisites(String courseID, String antirequisites) {
        removeFromTable("ANTIREQUISITES", "COURSE_ID", courseID);
        return addAntirequisites(courseID, antirequisites);
    }

    /**
     * Get a list of  courses
     *
     * @return a list of courses
     */
    public static ObservableList<Course> getCourses() {
        ResultSet rs = null;
        ObservableList<Course> courseList = FXCollections.observableArrayList();

        try {
            rs = databaseHandler.executeQuery("SELECT * FROM COURSES;");

            while (rs.next()) {
                courseList.add(new Course(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), getPrerequsites(rs.getString(1)), getAntirequisites(rs.getString(1)), rs.getString(5)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return courseList;
    }

    /**
     * Get a course with a specific ID
     *
     * @return a list of courses
     */
    public static Course getCourse(String courseID) {
        ResultSet rs = null;
        Course course = null;

        try {
            rs = databaseHandler.executeQuery("SELECT * FROM COURSES WHERE course_id = \'" + courseID + "\';");

            while (rs.next()) {

                course = new Course(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), getPrerequsites(rs.getString(1)), getAntirequisites(rs.getString(1)), rs.getString(5));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return course;
    }

    /**
     * Get prerequisites for a course with a specific ID
     * @return a string containing prerequistes
     */
    public static String getPrerequsites(String courseID) {
        ResultSet rs = null;
        ArrayList<String> prerequisites = new ArrayList<>();

        try {
            rs = databaseHandler.executeQuery("SELECT * FROM PREREQUISITES WHERE course_id = \'" + courseID + "\';");

            while (rs.next()) {

                prerequisites.add(rs.getString(2));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return String.join(",", prerequisites);
    }

    /**
     * Get antirequisites for a course with a specific ID
     * @return a string containing prerequistes
     */
    public static String getAntirequisites(String courseID) {
        ResultSet rs = null;
        ArrayList<String> antirequisites = new ArrayList<>();

        try {
            rs = databaseHandler.executeQuery("SELECT * FROM ANTIREQUISITES WHERE course_id = \'" + courseID + "\';");

            while (rs.next()) {
                antirequisites.add(rs.getString(2));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return String.join(",", antirequisites);
    }
}
