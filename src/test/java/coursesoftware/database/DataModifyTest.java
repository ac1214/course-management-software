package coursesoftware.database;

import org.junit.Before;
import org.junit.Test;
import coursesoftware.datatypes.Course;

import static junit.framework.TestCase.assertEquals;

public class DataModifyTest {
    @Before
    public void setupForTests() {
        // Setup department, and program for inserting programs
        DataModify.insertNewDepartment("TESTDEPT");
        DataModify.insertNewProgram("TESTPROG", "TESTDEPT");
    }

    @Before
    public void removeSetupForTests() {
        // Remove program and department that was inserted for test
        DataModify.removeProgram("TESTPROG");
        DataModify.removeDepartment("TESTDEPT");
    }
    @Test
    public void testCourseInsert() {
        Course toInsert = new Course("AAAA123", "TESTDEPT", 123, "A A A A", "", "", "");

        DataModify.insertNewCourse("AAAA123", "TESTDEPT", 123, "A A A A", "", "", "");

        Course courseFromDB = DataModify.getCourse("AAAA123");
        DataModify.removeCourse("AAAA123");

        assertEquals(toInsert, courseFromDB);
    }

    @Test
    public void testCourseRemoval() {
        DataModify.insertNewCourse("AAAA123", "TESTDEPT", 123, "A A A A", "", "", "");
        boolean successfulRemoval = DataModify.removeCourse("AAAA123");

        assertEquals(true, successfulRemoval);
        assertEquals(false, DataModify.checkCourseExists("AAAA123"));
    }

    @Test
    public void testProgramInsertRemove() {
        DataModify.insertNewProgram("TEST", "TESTDEPT");
        boolean programExists = DataModify.checkProgramExists("TEST");
        boolean successfulRemoval = DataModify.removeProgram("TEST");

        assertEquals(true, programExists);
        assertEquals(true, successfulRemoval);
    }

    @Test
    public void testDepartmentInsertRemove() {
        DataModify.insertNewDepartment("TEST");
        boolean departmentExists = DataModify.checkDepartmentExists("TEST");
        boolean successfulRemoval = DataModify.removeDepartment("TEST");

        assertEquals(true, departmentExists);
        assertEquals(true, successfulRemoval);
    }
}