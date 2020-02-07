import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DBConnection {

    private static final String USERNAME = "eci";
    private static final String PASSWORD = "eci123";
    private static final String CONN     = "jdbc:mysql://localhost:3306/eci";

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONN, USERNAME, PASSWORD);
    }
}