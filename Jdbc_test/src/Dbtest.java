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
     *  @param userID ID of the new user
     *  @return true if the user is in the database
     *          false if the user is not in the database
     */
    public static boolean findUser(Connection conn, int userID) {

        boolean userExists = false;

        // SQL statement
        String sql = "SELECT * FROM PERSON WHERE NAMEID = ?";

        // Create prepared statement
        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            // Insert values for the user
            ps.setInt(1, userID);

            // Execute the query
            ResultSet rs = ps.executeQuery();

            // Read the result of the query
            if (rs.next()) {

                // Read the NAMEID column of the query result
                String queryResult = rs.getString("NAMEID");

                // DEBUG
                //System.out.println("Query result: " + queryResult);

                // Record whether or not the user is already in the database
                if (!queryResult.equals(userID + "")){
                    System.out.println("User " + userID + " exists in the database already.");
                    userExists = true;
                }
            }
            else {
                System.out.println("User " + userID + " does not exist in the database.");
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
     *  @param userID ID of the new user
     *  @param name user's first and last name
     *  @param gender user's gender (M/F/O)
     *  @return 0 if successfully added user to the database
     *          1 if unsuccessful, the user already exists
     */
    public static int addUser(Connection conn, int userID, String name, String gender) {

        // Check if the user exists before adding
        boolean userExists;
        userExists = findUser(conn, userID);

        if (userExists) {
            System.out.println("Found user: " + userID + " - " + name + ".");
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
            ps.setInt(1, userID);
            ps.setString(2, name);
            ps.setInt(3, 0);
            ps.setString(4, gender);

            // Execute the prepared statement
            ps.executeUpdate();

            System.out.println("Successfully added user: " + userID + " - " + name + ".");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Successfully added the user
        return 0;
    }


    /**
     *  Adds points to a given user's account at a given restaurant.
     *
     *  @param conn connection object
     *  @param userID ID of the user
     *  @param restID ID of the restaurant
     *  @param points number of points to be added to the account
     *  @return 0 if successfully added points to the account
     *          1 if unsuccessful - either user or restaurant ID not found
     */
    public static int addPoints(Connection conn, int userID, int restID, int points) {

        boolean userExists;
        boolean restaurantExists;

        userExists = findUser(conn, userID);
        restaurantExists = findRestaurant(conn, restID);

        if (userExists && restaurantExists){

            // SQL statement
            String sql = "UPDATE POINTS SET POINT = POINT + ? WHERE NAMEID = ? AND RESTID = ?";

            // Create prepared statement
            try (PreparedStatement ps = conn.prepareStatement(sql))
            {
                // Insert values into the prepared statement
                ps.setInt(1, points);
                ps.setInt(2, userID);
                ps.setInt(3, restID);

                // Execute the query
                ps.executeUpdate();

                System.out.println("Successfully added " + points + " points to user " + userID + " in restaurant " + restID + ".");

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return 0;
        }
        else {
            return 1;
        }
    }



    /************************************************************
     *           RESTAURANT RELATED DATABASE FUNCTIONS
     ************************************************************/

    /**
     *  Find a restaurant in the database.
     *
     *  @param conn connection object
     *  @param restID ID of the new restaurant
     *  @return true if the restaurant is in the database
     *          false if the restaurant is no in the database
     */
    public static boolean findRestaurant(Connection conn, int restID) {

        boolean restaurantExists = false;

        // SQL statement
        String sql = "SELECT * FROM RESTAURANT WHERE RESTID = ?";

        // Create prepared statement
        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            // Insert values for the restaurant
            ps.setInt(1, restID);

            // Execute the query
            ResultSet rs = ps.executeQuery();

            // Read the result of the query
            if (rs.next()) {

                // Read the RESTID column of the query result
                String queryResult = rs.getString("RESTID");

                // Record whether or not the restaurant is already in the database
                if (!queryResult.equals(restID + "")){
                    System.out.println("Restaurant " + restID + " exists in the database already.");
                    restaurantExists = true;
                }
            }
            else {
                System.out.println("Restaurant " + restID + " does not exist in the database.");
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
     *  @param restID ID of the new restaurant
     *  @param name restaurant's name
     *  @param rewardsID ID of the restaurant's rewards list
     *  @return 0 if successfully added restaurant to the database
     *          1 if unsuccessful, the restaurant is already in the database
     */
    public static int addRestaurant(Connection conn, int restID, String name, int rewardsID) {

        // Check if the restaurant exists before adding
        boolean restaurantExists;
        restaurantExists = findRestaurant(conn, restID);

        if (restaurantExists) {
            System.out.println("Found restaurant: " + restID + " - " + name + ".");
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
            ps.setInt(1, restID);
            ps.setString(2, name);
            ps.setInt(3, rewardsID);

            // Execute the prepared statement
            ps.executeUpdate();

            System.out.println("Successfully added restaurant: " + restID + " - " + name + ".");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Successfully added the restaurant
        return 0;
    }


    /**
     *  Get the point value of a food from a restaurant.
     *
     *  @param conn connection object
     *  @param restID ID of the new restaurant
     *  @param foodID usually 1-8, represents the item on the list of restaurant's menu
     *  @return foodPoint the point value of the food item. if the item is not found, the value is 0
     */
    public static int getFoodPointValue(Connection conn, int restID, int foodID) {

        int foodPoint = 0;

        // Check if the restaurant exists before adding
        boolean restaurantExists;
        restaurantExists = findRestaurant(conn, restID);


        if (restaurantExists) {

            // SQL statement
            String sql = "SELECT POINT FROM FOOD WHERE RESTID = ? and FOODID = ?";

            // Create prepared statement
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // Insert values into the prepared statement
                ps.setInt(1, restID);
                ps.setInt(2, foodID);

                // Execute the query
                ResultSet rs = ps.executeQuery();

                // Read the result of the query
                if (rs.next()) {

                    // Read the POINT column of the query result
                    String queryResult = rs.getString("POINT");

                    // Convert string into integer point
                    foodPoint = Integer.parseInt(queryResult);
                } else {
                    System.out.println("Food ID not found.");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return foodPoint;
    }
}


