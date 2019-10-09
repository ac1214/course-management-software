package coursesoftware.database;

import java.sql.*;

public class DatabaseHandler {
    Connection connection = null;
    Statement statement = null;
    private String databaseURL = "jdbc:postgresql://localhost:5432/docker";

    public DatabaseHandler() {
        try {
            connection = DriverManager.getConnection(databaseURL, "docker", "docker");
        } catch (SQLException ex) {
            System.out.println("COULD NOT CONNECT TO DATABASE WITH URL " + databaseURL);
            ex.printStackTrace();
        }
        System.out.println("Connected to database with URL " + databaseURL);
    }

    public ResultSet executeQuery(String query) throws SQLException {
        ResultSet resultSet = null;

        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);

        return resultSet;
    }

    public void executeUpdate(String query) throws SQLException {
        ResultSet resultSet = null;

        statement = connection.createStatement();
        statement.executeUpdate(query);
    }
}
