import java.io.*;
import java.sql.*;

public class Dbtest {


    public static void main (String[] args) throws IOException {
        // Create connection object
        Connection conn;
        conn = connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        /* TEST TO ADD USER*/
        int userID;
        System.out.println("Enter user ID");
        userID = Integer.parseInt(br.readLine());

        addUser(conn, userID, "JLam", "M");

        System.out.println("Enter a user ID to be searched");
        userID = Integer.parseInt(br.readLine());

        findUser(conn, userID);
        /* END TEST */



        // Close the connection
        disconnect(conn);
    }


    /************************************************************
     *             DATABASE CONNECTION FUNCTIONS
     ************************************************************/

    /**
     *  Establishes a connection to the local SQLite database
     *
     *  @return connection object to the database
     */
    private static Connection connect() {

        // Create connection object
        Connection conn = null;

        try {

            // Database location and connection string
            String dbLocation = "sqlite/db/TRYBE.DB";
            String url = "jdbc:sqlite:" + dbLocation;

            conn = DriverManager.getConnection(url);

            // Connection confirmation
            System.out.println("Connection to SQLITE db is successful.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    /**
     *  Receives the connection object and closes the connection
     *
     *  @param conn connection object
     */
    private static void disconnect(Connection conn){
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connection successfully closed.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    /************************************************************
     *           USER RELATED DATABASE FUNCTIONS
     ************************************************************/


    /**
     *  Find a user in the database.
     *
     *  @param conn connection object
     *  @param ID ID of the new user
     */
    public static boolean findUser(Connection conn, int ID) {

        boolean userExists = false;

        // SQL statement
        String sql = "SELECT * FROM PERSON WHERE NAMEID = ?";

        // Create prepared statement
        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            // Insert values for the user
            ps.setInt(1, ID);

            // Execute the query
            ResultSet rs = ps.executeQuery();

            // Read the result of the query
            if (rs.next()) {

                // Read the NAMEID column of the query result
                String queryResult = rs.getString("NAMEID");

                // DEBUG
                //System.out.println("Query result: " + queryResult);

                // Record whether or not the user is already in the database
                if (!queryResult.equals(ID + "")){
                    System.out.println("User " + ID + " exists in the database already.");
                    userExists = true;
                }
            }
            else {
                System.out.println("User " + ID + " does not exist in the database.");
                userExists = false;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return userExists;
    }


    /**
     *  Add a new user to the database.
     *
     *  @param conn connection object
     *  @param ID ID of the new user
     *  @param name user's first and last name
     *  @param gender user's gender (M/F/O)
     */
    public static int addUser(Connection conn, int ID, String name, String gender) {

        // Check if the user exists before adding
        boolean userExists;
        userExists = findUser(conn, ID);

        if (userExists) {
            System.out.println("Found user: " + ID + " - " + name + ".");
            System.out.println("Unable to add user.");
            // Unsuccessful in adding user because they already exist
            return 1;
        }


        // SQL statement
        String sql = "INSERT INTO PERSON(NAMEID, NAME, POINTS, GENDER) VALUES(?,?,?,?)";

        // Create prepared statement
        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            // Insert values for the user
            ps.setInt(1, ID);
            ps.setString(2, name);
            ps.setInt(3, 0);
            ps.setString(4, gender);

            // Execute the prepared statement
            ps.executeUpdate();

            System.out.println("Successfully added user: " + ID + " - " + name + ".");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Successfully added the user
        return 0;
    }



    /************************************************************
     *           RESTAURANT RELATED DATABASE FUNCTIONS
     ************************************************************/

    /**
     *  Find a restaurant in the database.
     *
     *  @param conn connection object
     *  @param ID ID of the new restaurant
     */
    public static boolean findRestaurant(Connection conn, int ID) {

        boolean restaurantExists = false;

        // SQL statement
        String sql = "SELECT * FROM RESTAURANT WHERE RESTID = ?";

        // Create prepared statement
        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            // Insert values for the restaurant
            ps.setInt(1, ID);

            // Execute the query
            ResultSet rs = ps.executeQuery();

            // Read the result of the query
            if (rs.next()) {

                // Read the RESTID column of the query result
                String queryResult = rs.getString("RESTID");

                // Record whether or not the restaurant is already in the database
                if (!queryResult.equals(ID + "")){
                    System.out.println("Restaurant " + ID + " exists in the database already.");
                    restaurantExists = true;
                }
            }
            else {
                System.out.println("Restaurant " + ID + " does not exist in the database.");
                restaurantExists = false;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return restaurantExists;
    }


    /**
     *  Add a new restaurant to the database.
     *
     *  @param conn connection object
     *  @param ID ID of the new restaurant
     *  @param name restaurant's name
     *  @param rewardsID ID of the restaurant's rewards list
     */
    public static int addRestaurant(Connection conn, int ID, String name, int rewardsID) {

        // Check if the restaurant exists before adding
        boolean restaurantExists;
        restaurantExists = findRestaurant(conn, ID);

        if (restaurantExists) {
            System.out.println("Found restaurant: " + ID + " - " + name + ".");
            System.out.println("Unable to add restaurant.");
            // Unsuccessful in adding restaurant because they already exist
            return 1;
        }


        // SQL statement
        String sql = "INSERT INTO RESTAURANT(RESTID, RESTNAME, REWARDSID) VALUES(?,?,?)";

        // Create prepared statement
        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            // Insert values for the user
            ps.setInt(1, ID);
            ps.setString(2, name);
            ps.setInt(3, rewardsID);

            // Execute the prepared statement
            ps.executeUpdate();

            System.out.println("Successfully added restaurant: " + ID + " - " + name + ".");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Successfully added the restaurant
        return 0;
    }
}


