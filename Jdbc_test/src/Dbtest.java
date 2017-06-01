import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbtest {

    private static void connect() {
        Connection conn = null;
        try {
            String dbLocation = "sqlite/db/TRYBE.DB";
            String url = "jdbc:sqlite:" + dbLocation;
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLITE db is successful");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    public static void main (String[] args)
    {
        connect();
    }
}


