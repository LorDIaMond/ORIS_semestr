package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Di@M0nd7";
    private static final String URL = "jdbc:postgresql://localhost:5432/arsenal_website";

    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL driver loaded successfully");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        System.out.println("Connecting to: " + URL);
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
