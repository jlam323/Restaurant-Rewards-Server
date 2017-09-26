package com.trybe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;



@RestController
public class ServerController {


    //*************************************
    // TEST SERVER FUNCTIONS
    //*************************************

    // Simple endpoint for connectivity test. Returns a string.
    @RequestMapping(value="/", method=RequestMethod.POST)
    @ResponseBody
    public String home() {
        return "Hello World!";
    }

    // Tests by returning part of the URI that the user entered.
    @RequestMapping("/test/{word}")
    @ResponseBody
    public String returnWord(@PathVariable String word){
        return word;
    }


    // This returns a JSON when called.
    @RequestMapping("/sample")
    public SampleService sample(@RequestParam(value="name", defaultValue="World") String name) {
        String template = "Hello, %s!";
        AtomicLong counter = new AtomicLong();

        return new SampleService(counter.incrementAndGet(), String.format(template, name));
    }

    // Accepts a JSON and produces a JSON
    @RequestMapping(value="/placeholder", method=RequestMethod.POST, produces="application/json")
    @ResponseBody
    public void exampleEndpoint(){
    }


    //*************************************
    // SERVER API FUNCTIONS
    //*************************************


    // TODO: I feel like this shouldn't be its own endpoint... implement some login security thing in the future
    // TODO: also, remove the database disconnect when we add login
    @RequestMapping(value="/client/finduser/{userID}")
    @ResponseBody
    public ResponseEntity<Boolean> findUser(@PathVariable int userID) throws ClassNotFoundException{
        DatabaseManager dbm = new DatabaseManager();
        boolean found = dbm.findUser(userID);
        dbm.disconnect();

        if (found)
            return ResponseEntity.status(200).body(true);
        else
            return ResponseEntity.status(404).body(false);
    }


    // TODO: Consider sending a JSON instead of URI parameters. Applies to the other methods
    @RequestMapping(value="/client/adduser/{userID}/{name}/{gender}")
    @ResponseBody
    public ResponseEntity<Boolean> addUser(@PathVariable int userID, @PathVariable String name,
                                           @PathVariable String gender) throws ClassNotFoundException{
        DatabaseManager dbm = new DatabaseManager();
        boolean addedNewUser = dbm.addUser(userID, name, gender);

        if (addedNewUser)
            return ResponseEntity.status(201).body(true);
        else
            return ResponseEntity.status(500).body(false);
    }


    @RequestMapping(value="/client/getrewards/{restID}", produces="application/json")
    @ResponseBody
    public ResponseEntity<String> getRewards(@PathVariable int restID) throws ClassNotFoundException{
        DatabaseManager dbm = new DatabaseManager();
        Map<String,Integer> rewards = dbm.getRewards(restID);

        if (rewards == null)
            return ResponseEntity.status(404).body(null);
        else
            return ResponseEntity.status(200).body(convertToJson(rewards));
    }


    @RequestMapping(value="/client/viewallpoints/{userID}", produces="application/json")
    @ResponseBody
    public ResponseEntity<String> viewAllPoints(@PathVariable int userID) throws ClassNotFoundException{
        DatabaseManager dbm = new DatabaseManager();
        Map<String,Integer> points = dbm.viewAllPoints(userID);

        if (points == null)
            return ResponseEntity.status(404).body(null);
        else
            return ResponseEntity.status(200).body(convertToJson(points));
    }


    // TODO: I feel like this shouldn't be its own endpoint... implement some login security thing in the future
    // TODO: also, remove the database disconnect when we add login
    @RequestMapping(value="/restaurant/findrestaurant/{restID}")
    @ResponseBody
    public ResponseEntity<Boolean> findRestaurant(@PathVariable int restID) throws ClassNotFoundException{
        DatabaseManager dbm = new DatabaseManager();
        boolean found = dbm.findRestaurant(restID);
        dbm.disconnect();

        if (found)
            return ResponseEntity.status(200).body(true);
        else
            return ResponseEntity.status(404).body(false);
    }


    @RequestMapping(value="/restaurant/finduseratrestaurant/{userID}/{restID}")
    @ResponseBody
    public ResponseEntity<Boolean> findUserAtRestaurant(@PathVariable int userID, @PathVariable int restID) throws
            ClassNotFoundException{
        DatabaseManager dbm = new DatabaseManager();
        boolean found = dbm.findUserAtRestaurant(userID, restID);

        if (found)
            return ResponseEntity.status(200).body(true);
        else
            return ResponseEntity.status(404).body(false);
    }


    @RequestMapping(value="/restaurant/addrestaurant/{restID}/{name}/{rewardID}")
    @ResponseBody
    public ResponseEntity<Boolean> addRestaurant(@PathVariable int restID, @PathVariable String name,
                                                 @PathVariable int rewardID) throws ClassNotFoundException{
        DatabaseManager dbm = new DatabaseManager();
        boolean addedRestaurant = dbm.addRestaurant(restID, name, rewardID);

        if (addedRestaurant)
            return ResponseEntity.status(201).body(true);
        else
            return ResponseEntity.status(500).body(false);
    }


    @RequestMapping(value="/restaurant/getfoodinformation/{restID}")
    @ResponseBody
    public ResponseEntity<String> getFoodInformation(@PathVariable int restID) throws ClassNotFoundException{
        DatabaseManager dbm = new DatabaseManager();
        ArrayList<FoodItem> foodList = dbm.getFoodInformation(restID);

        if (foodList == null)
            return ResponseEntity.status(404).body(null);
        else
            return ResponseEntity.status(200).body(convertToJson(foodList));
    }


    @RequestMapping(value="/restaurant/addreward/{restID}/{rewardText}/{rewardValue}/{rewardID}")
    @ResponseBody
    public ResponseEntity<Boolean> addReward(@PathVariable int restID, @PathVariable String rewardText,
                                             @PathVariable int rewardValue, @PathVariable int rewardID) throws
            ClassNotFoundException{
        DatabaseManager dbm = new DatabaseManager();
        boolean addedReward = dbm.addReward(restID, rewardText, rewardValue, rewardID);

        if (addedReward)
            return ResponseEntity.status(201).body(true);
        else
            return ResponseEntity.status(500).body(false);
    }


    @RequestMapping(value="/restaurant/removereward/{restID}/{rewardID}")
    @ResponseBody
    public ResponseEntity<Boolean> removeReward(@PathVariable int restID, @PathVariable int rewardID) throws
            ClassNotFoundException{
        DatabaseManager dbm = new DatabaseManager();
        boolean removedReward = dbm.removeReward(restID, rewardID);

        if (removedReward)
            return ResponseEntity.status(201).body(true);
        else
            return ResponseEntity.status(500).body(false);
    }


    @RequestMapping(value="/restaurant/redeemreward/{userID}/{restID}/{points}")
    @ResponseBody
    public ResponseEntity<Boolean> redeemReward(@PathVariable int userID, @PathVariable int restID,
                                                @PathVariable int points) throws ClassNotFoundException{
        DatabaseManager dbm = new DatabaseManager();
        boolean removedReward = dbm.redeemReward(userID, restID, points);

        if (removedReward)
            return ResponseEntity.status(200).body(true);
        else
            return ResponseEntity.status(500).body(false);
    }


    @RequestMapping(value="/restaurant/recordPurchase/{userID}/{restID}/points/{foodID}")
    @ResponseBody
    public ResponseEntity<Boolean> recordPurchase(@PathVariable int userID, @PathVariable int restID,
                                                @PathVariable int points, @PathVariable String foodID) throws
            ClassNotFoundException{
        DatabaseManager dbm = new DatabaseManager();
        boolean recorded = dbm.recordPurchase(userID, restID, points, foodID);

        if (recorded)
            return ResponseEntity.status(201).body(true);
        else
            return ResponseEntity.status(500).body(false);
    }




    public static void main(String[] args) throws Exception {
        SpringApplication.run(ServerController.class, args);
    }

    private <T> String convertToJson(T data){
        // GSON library to convert incoming data to JSON
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return gson.toJson(data);
    }
}
