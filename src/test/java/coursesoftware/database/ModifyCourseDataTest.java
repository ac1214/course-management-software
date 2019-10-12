package coursesoftware.database;

import coursesoftware.datatypes.Course;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ModifyCourseDataTest {
    @Before
    public void setupForTests() {
        // Setup department, and program for inserting courses
        ModifyDepartmentData.insertNewDepartment("TESTDEPT");
        ModifyProgramData.insertNewProgram("TESTPROG", "TESTDEPT");
    }

    @After
    public void removeSetupForTests() {
        // Remove program and department that was inserted for test
        ModifyProgramData.removeProgram("TESTPROG");
        ModifyDepartmentData.removeDepartment("TESTDEPT");
    }

    @Test
    public void testCourseInsert() {
        Course toInsert = new Course("AAAA123", "TESTDEPT", 123, "A A A A", "", "", "");

        ModifyCourseData.insertNewCourse("AAAA123", "TESTDEPT", 123, "A A A A", "", "", "");

        Course courseFromDB = ModifyCourseData.getCourse("AAAA123");
        ModifyCourseData.removeCourse("AAAA123");

        assertEquals(toInsert, courseFromDB);
    }

    @Test
    public void testCourseRemoval() {
        ModifyCourseData.insertNewCourse("AAAA123", "TESTDEPT", 123, "A A A A", "", "", "");
        boolean successfulRemoval = ModifyCourseData.removeCourse("AAAA123");

        assertEquals(true, successfulRemoval);
        assertEquals(false, ModifyCourseData.checkCourseExists("AAAA123"));
    }
}
