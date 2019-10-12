package coursesoftware.database;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ModifyUserDataTest {
    @Test
    public void testAdminUserInsertRemove() {
        ModifyUserData.addUser("TESTUSER", "123123", true);
        int validationResult = ModifyUserData.validateUser("TESTUSER", "123123");

        assertEquals(1, validationResult);
        assertEquals(true, ModifyUserData.removeUser("TESTUSER"));
    }

    @Test
    public void testRegularUserInsertRemove() {
        ModifyUserData.addUser("TESTUSER", "123123", false);
        int validationResult = ModifyUserData.validateUser("TESTUSER", "123123");

        assertEquals(2, validationResult);
        assertEquals(true, ModifyUserData.removeUser("TESTUSER"));
    }

    @Test
    public void testInvalidPassword() {
        ModifyUserData.addUser("TESTUSER", "123123", true);
        int validationResult = ModifyUserData.validateUser("TESTUSER", "456456");

        assertEquals(-1, validationResult);
        assertEquals(true, ModifyUserData.removeUser("TESTUSER"));
    }

    @Test
    public void testChangePassword() {
        ModifyUserData.addUser("TESTUSER", "123123", true);
        ModifyUserData.changePassword("TESTUSER", "456456");

        int validationOldPW = ModifyUserData.validateUser("TESTUSER", "123456");
        int validationNewPW = ModifyUserData.validateUser("TESTUSER", "456456");

        assertEquals(-1, validationOldPW);
        assertEquals(1, validationNewPW);
        assertEquals(true, ModifyUserData.removeUser("TESTUSER"));
    }
}
