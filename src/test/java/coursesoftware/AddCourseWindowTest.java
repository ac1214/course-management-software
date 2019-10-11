package coursesoftware;

import coursesoftware.database.DataModify;
import coursesoftware.datatypes.Course;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class AddCourseWindowTest {
    @Before
    public void setupForTests() {
        // Setup test department, and courses
        DataModify.insertNewDepartment("TESTDEPT");
        Course testPrereq = new Course("PRER123", "TESTDEPT", "123", "Test prerequisite", "", "", "");
        Course testAntireq = new Course("ANTI123", "TESTDEPT", "123", "Test antirequisite", "", "", "");

        DataModify.insertNewCourse(testPrereq);
        DataModify.insertNewCourse(testAntireq);
    }

    @After
    public void removeSetupForTests() {
        // Remove courses and department that was inserted for test
        DataModify.removeCourse("PRER123");
        DataModify.removeCourse("ANTI123");
        DataModify.removeDepartment("TESTDEPT");
    }

    @Test
    public void validateValidCourseTest() {
        Course testCourse = new Course("TEST123", "TESTDEPT", "123", "Test course", "", "", "Test description");

        assertEquals(true, AddCourseWindow.validateCourse(testCourse));
    }

    @Test
    public void validateValidCourseTestWithPrerequsite() {
        Course testCourse = new Course("TEST123", "TESTDEPT", "123", "Test course", "PRER123", "", "Test description");

        assertEquals(true, AddCourseWindow.validateCourse(testCourse));
    }

    @Test
    public void validateValidCourseTestWithantirequisite() {
        Course testCourse = new Course("TEST123", "TESTDEPT", "123", "Test course", "", "ANTI123", "Test description");

        assertEquals(true, AddCourseWindow.validateCourse(testCourse));
    }

    @Test(expected = NoClassDefFoundError.class)
    public void invalidCourseIDTest() {
        Course testCourse = new Course("INVALIDCOURSE", "TESTDEPT", "123", "Test course", "", "", "Test description");

        AddCourseWindow.validateCourse(testCourse);
    }

    @Test(expected = NoClassDefFoundError.class)
    public void invalidPrerequisitesTest() {
        Course testCourse = new Course("TEST123", "TESTDEPT", "123", "Test course", "INVALIDCOURSE1,INVALIDCOURSE2", "", "Test description");

        AddCourseWindow.validateCourse(testCourse);
    }

    @Test(expected = ExceptionInInitializerError.class)
    public void invalidAntirequisitesTest() {
        Course testCourse = new Course("TEST123", "TESTDEPT", "123", "Test course", "", "INVALIDCOURSE1,INVALIDCOURSE2", "Test description");

        AddCourseWindow.validateCourse(testCourse);
    }
}
