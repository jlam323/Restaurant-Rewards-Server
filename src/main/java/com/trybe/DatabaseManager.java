package com.trybe;

import java.sql.*;
import java.util.*;


public class DatabaseManager {

    private Connection conn;

    public DatabaseManager() throws ClassNotFoundException{
        connect();
    }


    /************************************************************
     *             DATABASE CONNECTION FUNCTIONS
     ************************************************************/

    /**
     *  Establishes a connection to the local SQLite database.
     */
    private void connect() throws ClassNotFoundException{

        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        try {
            // Database location and connection string
            String dbLocation = "sqlite/db/TRYBE.db";
            String url = "jdbc:sqlite:" + dbLocation;

            conn = DriverManager.getConnection(url);

            // Connection confirmation
            System.out.println("Connection to SQLITE db is successful.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Receives the connection object and closes the connection
     */
    public void disconnect(){
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
     *  @param userID ID of the new user
     *  @return true if the user is in the database
     *          false if the user is not in the database
     */
    public boolean findUser(int userID) {

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
                if (queryResult.equals(userID + "")){
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
     *  @param userID ID of the new user
     *  @param name user's first and last name
     *  @param gender user's gender (M/F/O)
     *  @return true if successfully added user to the database
     *          false if unsuccessful, the user already exists
     */
    public boolean addUser(int userID, String name, String gender) {

        // Check if the user exists before adding
        boolean userExists;
        userExists = findUser(userID);

        if (userExists) {
            System.out.println("Found user: " + userID + " - " + name + ".");
            System.out.println("Unable to add user.");
            // Unsuccessful in adding user because they already exist
            return false;
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
        } finally {
            disconnect();
        }

        // Successfully added the user
        return true;
    }


    /**
     *  Gets all the rewards that can be redeemed by points at a particular restaurant.
     *
     *  @param restID ID of the new restaurant
     *  @return rewards A map of integer point values to the reward name
     */
    public Map<String,Integer> getRewards(int restID) {

        // Check if the restaurant exists before adding
        boolean restaurantExists;
        restaurantExists = findRestaurant(restID);

        // Create map of rewards, each has a point value and description
        Map<String,Integer> rewards = new HashMap<String,Integer>();


        if (restaurantExists) {

            // SQL statement
            String sql = "SELECT * FROM REWARDS WHERE RESTID = ?";

            // Create prepared statement
            try (PreparedStatement ps = conn.prepareStatement(sql))
            {
                // Insert values for the restaurant
                ps.setInt(1, restID);

                // Execute the query
                ResultSet rs = ps.executeQuery();

                Integer rewardValue;
                String rewardText;

                // Read the result of the query. Max of 10 rewards
                while (rs.next()) {

                    // Read the reward text and value
                    rewardValue = rs.getInt("REDEMPTION");
                    rewardText = rs.getString("REWARD");

                    // Add pair of reward text and value into the map
                    rewards.put(rewardText, rewardValue);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                disconnect();
            }
        }

        return rewards;
    }


    /**
     *  Retrieves all the points that the user owns at all restaurants.
     *
     *  @param userID ID of the user
     *  @return points A map of points to restaurants
     */
    public Map<String,Integer> viewAllPoints(int userID) {

        boolean userExists;
        userExists = findUser(userID);

        Map<String,Integer> points = new HashMap<String,Integer>();

        if (userExists) {

            // SQL statement
            String sql = "SELECT * FROM POINTS WHERE NAMEID = ?";

            // Create prepared statement
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                // Insert values into the prepared statement
                ps.setInt(1, userID);

                // Execute the query
                ResultSet rs = ps.executeQuery();

                String restID;
                int pointsAtRestaurant;

                while (rs.next()) {
                    restID = rs.getString("RESTID");
                    pointsAtRestaurant = rs.getInt("POINTS");

                    // Insert pair into the map
                    points.put(restID, pointsAtRestaurant);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                disconnect();
            }
        }

        return points;
    }



    /************************************************************
     *           RESTAURANT RELATED DATABASE FUNCTIONS
     ************************************************************/

    /**
     *  Find a restaurant in the database.
     *
     *  @param restID ID of the new restaurant
     *  @return true if the restaurant is in the database
     *          false if the restaurant is no in the database
     */
    public boolean findRestaurant(int restID) {

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
                if (queryResult.equals(restID + "")){
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


    /** Find if a restaurant and user exists in rewards table
     *
     * @param restID ID of the restaurant
     * @param userID ID of the user
     * @return true if combination of restid and userid exists
     *         false if combination of restid and userid doesnt exist
     */
    public boolean findUserAtRestaurant(int restID, int userID) {

        boolean userExists = false;

        // sql statement
        String sql = "SELECT * FROM REWARDS WHERE RESTID = ? and NAMEID = ?";

        // Create prepared statement
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            // Insert values for the restaurant
            ps.setInt(1, restID);
            ps.setInt(2, userID);

            // Execute the query
            ResultSet rs = ps.executeQuery();

            // Read the result of the query
            if (rs.next()) {
                userExists = true;
            }
            else{
                userExists = false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            disconnect();
        }

        return userExists;
    }


    /**
     *  Adds points to a given user's account at a given restaurant. Invoked by recordPurchase method.
     *
     *  @param userID ID of the user
     *  @param restID ID of the restaurant
     *  @param points number of points to be added to the account
     *  @return true if successfully added points to the account
     *          false if unsuccessful - either user or restaurant ID not found
     */
    private boolean addPoints(int userID, int restID, int points) {

        boolean userExists;
        boolean restaurantExists;
        boolean userPointsExists;

        userExists = findUser(userID);
        restaurantExists = findRestaurant(restID);

        // Check if the user has a point counter with the restaurant
        userPointsExists = findUserAtRestaurant(restID, userID);

        if (userExists && restaurantExists && userPointsExists) {

            // SQL statement
            String sql = "UPDATE POINTS SET POINT = POINT + ? WHERE NAMEID = ? AND RESTID = ?";

            // Create prepared statement
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // Insert values into the prepared statement
                ps.setInt(1, points);
                ps.setInt(2, userID);
                ps.setInt(3, restID);

                // Execute the query
                ps.executeUpdate();

                System.out.println("Successfully added " + points + " points to user " + userID + " in restaurant " + restID + ".");

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                disconnect();
            }

            return true;
        }
        else
            return false;
    }


    /**
     *  Add a new restaurant to the database.
     *
     *  @param restID ID of the new restaurant
     *  @param name restaurant's name
     *  @param rewardID ID of the restaurant's rewards list
     *  @return true if successfully added restaurant to the database
     *          false if unsuccessful, the restaurant is already in the database
     */
    public boolean addRestaurant(int restID, String name, int rewardID) {

        // Check if the restaurant exists before adding
        boolean restaurantExists;
        restaurantExists = findRestaurant(restID);

        if (restaurantExists) {
            System.out.println("Found restaurant: " + restID + " - " + name + ".");
            System.out.println("Unable to add restaurant.");
            // Unsuccessful in adding restaurant because they already exist
            return false;
        }


        // SQL statement
        String sql = "INSERT INTO RESTAURANT(RESTID, RESTNAME, REWARDSID) VALUES(?,?,?)";

        // Create prepared statement
        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            // Insert values for the user
            ps.setInt(1, restID);
            ps.setString(2, name);
            ps.setInt(3, rewardID);

            // Execute the prepared statement
            ps.executeUpdate();

            System.out.println("Successfully added restaurant: " + restID + " - " + name + ".");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            disconnect();
        }

        // Successfully added the restaurant
        return true;
    }


    /**
     *  Get the food list and point values for each for the restaurant app.
     *
     *  @param restID ID of the new restaurant
     *  @return foodList the list of food objects that contain an ID and point value
     */
    public ArrayList<FoodItem> getFoodInformation(int restID) {

        // Check if the restaurant exists before adding
        boolean restaurantExists;
        restaurantExists = findRestaurant(restID);

        // Create an array list to store the food objects
        ArrayList<FoodItem> foodList = new ArrayList<FoodItem>();

        // Create a food item object to store food information from the database
        FoodItem food;

        if (restaurantExists) {

            // SQL statement
            String sql = "SELECT * FROM FOOD WHERE RESTID = ?";

            // Create prepared statement
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                // Insert values into the prepared statement
                ps.setInt(1, restID);

                // Execute the query
                ResultSet rs = ps.executeQuery();


                int foodValue;
                String foodID;

                // Read the result of the query
                while (rs.next()) {

                    // Read the information from the columns
                    foodValue = rs.getInt("VALUE");
                    foodID = rs.getString("FOODID");

                    // Create an object with the attributes
                    food = new FoodItem(foodValue, foodID);

                    // Put food object into the array list
                    foodList.add(food);
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                disconnect();
            }
        }
        return foodList;
    }


    /**
     *  Adds a reward option to the restaurant's list of rewards.
     *
     *  @param restID ID of the new restaurant
     *  @param rewardText text description of the reward
     *  @param rewardValue number of points that the reward is worth
     *  @param rewardID ID of the reward
     *
     *  @return true if successfully added the new reward
     *          false if unsuccessful. Reward already exists
     */
    public boolean addReward(int restID, String rewardText, int rewardValue, int rewardID) {

        // Check if the restaurant exists before adding
        boolean restaurantExists;
        restaurantExists = findRestaurant(restID);


        if (restaurantExists) {

            // SQL statement
            String sql = "INSERT INTO REWARDS (REWARDSID, RESTID, REWARD, REDEMPTION) VALUES (?,?,?,?)";

            // Create prepared statement
            try (PreparedStatement ps = conn.prepareStatement(sql))
            {
                // Insert values
                ps.setInt(1, rewardID);
                ps.setInt(2, restID);
                ps.setString(3, rewardText);
                ps.setInt(4, rewardValue);

                // Execute the update
                ps.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                disconnect();
            }
            return true;
        }
        else
            return false;
    }


    /**
     *  Removes a reward option to the restaurant's list of rewards.
     *
     *  @param restID ID of the new restaurant
     *  @param rewardID ID of the reward
     *
     *  @return true if successfully removed the new reward
     *          false if unsuccessful. Restaurant or reward does not exist
     */
    public boolean removeReward(int restID, int rewardID) {

        // Check if the restaurant exists before adding
        boolean restaurantExists;
        restaurantExists = findRestaurant(restID);


        if (restaurantExists) {

            // SQL statement
            String sql = "DELETE WHERE RESTID = ? AND REWARDID = ? FROM TABLE REWARDS ";

            // Create prepared statement
            try (PreparedStatement ps = conn.prepareStatement(sql))
            {
                // Insert values
                ps.setInt(1, restID);
                ps.setInt(2, rewardID);

                // Execute the update
                ps.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                disconnect();
            }
            return true;
        }
        else
            return false;

    }


    /**
     *  Subtracts points from a given user's account at a given restaurant from a reward redemption.
     *
     *  @param userID ID of the user
     *  @param restID ID of the restaurant
     *  @param points number of points to be subtracted from the account
     *  @return true if successfully subtracted points to the account
     *          false if unsuccessful - either user or restaurant ID not found
     */
    public boolean redeemReward(int userID, int restID, int points) {

        boolean userExists;
        boolean restaurantExists;

        userExists = findUser(userID);
        restaurantExists = findRestaurant(restID);

        if (userExists && restaurantExists){

            // SQL statement
            String sql = "UPDATE POINTS SET POINT = POINT - ? WHERE NAMEID = ? AND RESTID = ?";

            // Create prepared statement
            try (PreparedStatement ps = conn.prepareStatement(sql))
            {
                // Insert values into the prepared statement
                ps.setInt(1, points);
                ps.setInt(2, userID);
                ps.setInt(3, restID);

                // Execute the query
                ps.executeUpdate();

                System.out.println("Successfully redeemed. Subtracted " + points + " points to user " + userID + " in restaurant " + restID + ".");

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                disconnect();
            }
            return true;
        }
        else
            return false;

    }


    /**
     *  Records a user's purchase
     *
     *  @param restID ID of the new restaurant
     *  @param foodID ID of the reward
     *
     */
    public boolean recordPurchase(int userID, int restID, int points, String foodID) {
        boolean added = addPoints(userID, restID, points);

        if (!added){
            //TODO: throw some error or do something
            System.out.println("[recordPurchase] there was an error adding points");
        }

        String sql = "INSERT INTO PURCHASE_HISTORY(NAMEID, RESTID, , FOODID) VALUES(?,?,?)";

        // Create prepared statement
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Insert values for the user
            ps.setInt(1, userID);
            ps.setInt(2, restID);
            ps.setString(3, foodID);

            // Execute the prepared statement
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            disconnect();
        }

        // Successfully added the user
        return true;
    }






    /*
    public static void main (String[] args) throws IOException {
        // Create connection object
        Connection conn;
        conn = connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        // TEST TO ADD USER
        int userID;
        System.out.println("Enter user ID");
        userID = Integer.parseInt(br.readLine());

        addUser(conn, userID, "JLam", "M");

        System.out.println("Enter a user ID to be searched");
        userID = Integer.parseInt(br.readLine());

        findUser(conn, userID);
        // END TEST



        // Close the connection
        disconnect(conn);
    }
    */
}



