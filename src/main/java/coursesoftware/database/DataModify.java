package coursesoftware.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class DataModify {
    protected static DatabaseHandler databaseHandler = null;

    static {
        databaseHandler = new DatabaseHandler();
    }

    public static boolean insertToTable(String table, String[] values) {
        StringBuilder query = new StringBuilder("INSERT INTO " + table + " VALUES (");
        for (int i = 0; i < values.length - 1; i++) {
            query.append('\'');
            query.append(values[i]);
            query.append("\', ");
        }

        // append last value to query
        query.append('\'');
        query.append(values[values.length - 1] + "\');");

        try {
            databaseHandler.executeUpdate(query.toString());
            return true;
        } catch (SQLException ex) {
            System.out.println("Could not insert values " + Arrays.toString(values) + " into table " + table);
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean checkElementExists(String table, String colName, String value) {
        int count = 0;
        ResultSet rs = null;

        try {
            rs = databaseHandler.executeQuery("SELECT * FROM " + table + " WHERE " + colName + " = \'" + value + "\';");

            while (rs.next()) {
                count++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return count > 0;
    }

    public static boolean removeFromTable(String table, String colName, String value) {
        try {
            databaseHandler.executeUpdate("DELETE FROM " + table + " WHERE " + colName + " = \'" + value + "\';");
            return true;
        } catch (SQLException ex) {
            System.out.println("Could not remove " + value + " from " + table);
            ex.printStackTrace();
            return false;
        }
    }
}
