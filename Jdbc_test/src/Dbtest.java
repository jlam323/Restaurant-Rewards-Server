/**
 * Created by 100628824 on 6/1/2017.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbtest {

    public static void connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:C:/sqlite/db/TRYBE.DB";
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


