package coursesoftware.database;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ModifyDepartmentDataTest {
    @Test
    public void testDepartmentInsertRemove() {
        ModifyDepartmentData.insertNewDepartment("TEST");
        boolean departmentExists = ModifyDepartmentData.checkDepartmentExists("TEST");
        boolean successfulRemoval = ModifyDepartmentData.removeDepartment("TEST");

        assertEquals(true, departmentExists);
        assertEquals(true, successfulRemoval);
    }
}
