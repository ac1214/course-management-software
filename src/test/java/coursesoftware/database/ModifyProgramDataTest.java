package coursesoftware.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ModifyProgramDataTest {
    @Before
    public void setupForTests() {
        // Setup department, and program for inserting programs
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
    public void testProgramInsertRemove() {
        ModifyProgramData.insertNewProgram("TEST", "TESTDEPT");
        boolean programExists = ModifyProgramData.checkProgramExists("TEST");
        boolean successfulRemoval = ModifyProgramData.removeProgram("TEST");

        assertEquals(true, programExists);
        assertEquals(true, successfulRemoval);
    }
}
