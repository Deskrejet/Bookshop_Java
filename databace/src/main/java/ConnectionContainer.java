import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionContainer {
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "1234";
    private static final String URL = "jdbc:postgresql://localhost:5432/shop";
    private static final String DRIVER = "org.postgresql.Driver";
    private Connection connection;
    public ConnectionContainer() {
        this.connection = createConnectiob();
    }
    private Connection createConnectiob() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public Connection getConnection() {
        return connection;
    }
    public Statement getStatement() throws SQLException {
        return connection.createStatement();
    }
}

