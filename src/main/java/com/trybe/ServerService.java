package com.trybe;

import model.*;
import repository.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
@SuppressWarnings("WeakerAccess")
public class ServerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PointsRepository pointsRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private RewardRepository rewardRepository;
    @Autowired
    private RewardTransactionRepository rewardTransactionRepository;
    @Autowired
    private TransactionRepository transactionRepository;



    public ServerService(){}


    //----------CREATE FUNCTIONS----------//
    public String createCustomer(String id, String firstName,String lastName, String gender){
        if(customerRepository.exists(id))
            return convertToJson("Error creating customer: " + id + " already exists.");
        else
            saveCustomer(id, firstName, lastName, gender, getDate(), getDate());
        return convertToJson("Successfully created customer ID: " + id);
    }

    public String createPoints(String custId, String id, int amount){
        if(pointsRepository.exists(id))
            return convertToJson("Error creating points: " + id + " already exists.");
        else if(!customerRepository.exists(custId))
            return convertToJson("Error creating points, customer " + custId + " doesn't exist.");
        else {
            Customer customer = customerRepository.findOne(custId);
            savePoints(id, amount, customer, getDate(), getDate());
            return convertToJson("Successfully created customer ID: " + id);
        }
    }

    public String createRestaurant(String id, String name){
        if(restaurantRepository.exists(id))
            return convertToJson("Error creating restaurant: " + id + " already exists.");
        else
            saveRestaurant(id, name, getDate(), getDate());
        return convertToJson("Successfully created restaurant ID: " + id);
    }

    public String createReward(String restId, String id, String text, int pointsCost){
        if(rewardRepository.exists(id))
            return convertToJson("Error creating reward: " + id + " already exists.");
        else if(!restaurantRepository.exists(restId))
            return convertToJson("Error creating reward, restaurant " + restId + " doesn't exist.");
        else {
            Restaurant restaurant = restaurantRepository.findOne(restId);
            saveReward(id, text, pointsCost, restaurant, getDate(), getDate());
            return convertToJson("Successfully created reward ID: " + id);
        }
    }

    public String createRewardTransaction(String custId, String id, Date date, int pointsCost){
        if(rewardTransactionRepository.exists(id))
            return convertToJson("Error creating reward transaction: " + id + " already exists.");
        else if(!customerRepository.exists(custId))
            return convertToJson("Error creating reward transaction, customer " + custId + " doesn't exist.");
        else {
            Customer customer = customerRepository.findOne(custId);
            saveRewardTransaction(id, date, pointsCost, customer, getDate(), getDate());
            return convertToJson("Successfully created reward transaction ID: " + id);
        }
    }

    public String createTransaction(String custId, String restId, String id, Date date, double amount){
        if(transactionRepository.exists(id))
            return convertToJson("Error creating transaction: " + id + " already exists.");
        else if(!customerRepository.exists(custId))
            return convertToJson("Error creating transaction, customer " + custId + " doesn't exist.");
        else if(!restaurantRepository.exists(restId))
            return convertToJson("Error creating transaction, restaurant " + restId + " doesn't exist.");
        else{
            Customer customer = customerRepository.findOne(custId);
            Restaurant restaurant = restaurantRepository.findOne(restId);
            saveTransaction(id, date, amount, customer, restaurant, getDate(), getDate());
            return convertToJson("Successfully created transaction ID: " + id);
        }
    }




    //----------UPDATE FUNCTIONS----------//
    public String updateCustomer(String id, String firstName, String lastName, String gender){
        if(customerRepository.exists(id)){
            Customer customer = customerRepository.findOne(id);
            saveCustomer(id, firstName, lastName, gender, customer.getCreateDate(), getDate());
            return convertToJson("Successfully updated customer ID: " + id);
        }
            return convertToJson("Error updating customer: " + id + " doesn't exist.");
    }

    public String updatePoints(String id, int amount) {
        if(pointsRepository.exists(id)){
            Points points = pointsRepository.findOne(id);
            savePoints(id, amount, points.getCustomer(), points.getCreateDate(), getDate());
            return convertToJson("Successfully updated points ID: " + id);
        }
        return convertToJson("Error updating points: " + id + " doesn't exist.");
    }

    public String updateRestaurant(String id, String name) {
        if(restaurantRepository.exists(id)){
            Restaurant restaurant = restaurantRepository.findOne(id);
            saveRestaurant(id, name, restaurant.getCreateDate(), getDate());
            return convertToJson("Successfully updated restaurant ID: " + id);
        }
        return convertToJson("Error updating restaurant: " + id + " doesn't exist.");
    }

    public String updateReward(String id, String text, int pointsCost){
        if(rewardRepository.exists(id)){
            Reward reward = rewardRepository.findOne(id);
            saveReward(id, text, pointsCost, reward.getRestaurant(), reward.getCreateDate(), getDate());
            return convertToJson("Successfully updated reward ID: " + id);
        }
        return convertToJson("Error updating reward: " + id + " doesn't exist.");
    }

    public String updateRewardTransaction(String id, Date date, int pointsCost){
        if(rewardTransactionRepository.exists(id)){
            RewardTransaction rt = rewardTransactionRepository.findOne(id);
            saveRewardTransaction(id, date, pointsCost, rt.getCustomer(), rt.getCreateDate(), getDate());
            return convertToJson("Successfully updated rewards transaction ID: " + id);
        }
        return convertToJson("Error updating rewards transaction: " + id + " doesn't exist.");
    }

    public String updateTransaction(String id, Date date, double amount){
        if(transactionRepository.exists(id)){
            Transaction transaction = transactionRepository.findOne(id);
            saveTransaction(id, date, amount, transaction.getCustomer(), transaction.getRestaurant(),
                    transaction.getCreateDate(), getDate());
            return convertToJson("Successfully updated transaction ID: " + id);
        }
        return convertToJson("Error updating transaction: " + id + " doesn't exist.");
    }




    //----------SAVE REPOSITORY FUNCTIONS----------//
    public void saveCustomer(String id, String firstName, String lastName, String gender, Date createDate,
                             Date lastUpdated){
        Customer customer = new Customer(id, firstName, lastName, gender, createDate, lastUpdated);
        customerRepository.save(customer);
    }

    public void savePoints(String id, int amount, Customer customer, Date createDate, Date lastUpdated) {
        Points points = new Points(id, amount, customer, createDate, lastUpdated);
        pointsRepository.save(points);
    }

    public void saveRestaurant(String id, String name, Date createDate, Date lastUpdated){
        Restaurant restaurant = new Restaurant(id, name, createDate, lastUpdated);
        restaurantRepository.save(restaurant);
    }

    public void saveReward(String id, String text, int pointsCost, Restaurant restaurant, Date createDate,
                           Date lastUpdated){
        Reward reward = new Reward (id, text, pointsCost, restaurant, createDate, lastUpdated);
        rewardRepository.save(reward);
    }

    public void saveRewardTransaction(String id, Date date, int pointsCost, Customer customer, Date createDate,
                                      Date lastUpdated){
        RewardTransaction rt = new RewardTransaction((id, date, pointsCost, customer, createDate, lastUpdated));
        rewardTransactionRepository.save(rt);
    }

    public void saveTransaction(String id, Date date, double amount, Customer customer, Restaurant restaurant,
                                Date createDate, Date lastUpdated){
        Transaction transaction = new Transaction(id, date, amount, customer, restaurant, createDate, lastUpdated);
        transactionRepository.save(transaction);
    }




    //----------SEARCH FOR FUNCTIONS----------//
    // These functions will be expanded on later as necessary with additional query parameters.

    public String searchCustomer(String id){
        Customer customer = customerRepository.findOne(id);
        return convertToJson(customer);
    }

    public String searchPoints(String id){
        Points points = pointsRepository.findOne(id);
        return convertToJson(points);
    }

    public String searchRestaurant(String id){
        Restaurant restaurant = restaurantRepository.findOne(id);
        return convertToJson(restaurant);
    }

    public String searchReward(String id){
        Reward reward = rewardRepository.findOne(id);
        return convertToJson(reward);
    }

    public String searchRewardTransaction(String id){
        RewardTransaction rt = rewardTransactionRepository.findOne(id);
        return convertToJson(rt);
    }

    public String searchTransaction(String id){
        Transaction transaction = transactionRepository.findOne(id);
        return convertToJson(transaction);
    }




    //----------DELETE FUNCTIONS----------//
    final String SUCCESS = "SUCCESS";
    final String ERROR = "ERROR";
    //StringBuilder errorResponse;

    public String deleteCustomer(String id){
        customerRepository.delete(id);
        return convertToJson(SUCCESS);
    }

    public String deletePoints(String id){
        pointsRepository.delete(id);
        return convertToJson(SUCCESS);
    }

    public String deleteRestaurant(String id){
        restaurantRepository.delete(id);
        return convertToJson(SUCCESS);
    }

    public String deleteReward(String id){
        rewardRepository.delete(id);
        return convertToJson(SUCCESS);
    }

    public String deleteRewardTransaction(String id){
        rewardTransactionRepository.delete(id);
        return convertToJson(SUCCESS);
    }

    public String deleteTransaction(String id){
        transactionRepository.delete(id);
        return convertToJson(SUCCESS);
    }




    // Get the date and convert to sql.Date format
    public Date getDate(){
        return Date.valueOf(LocalDate.now());
    }

    // Convert a string into SQL date format
    public Date stringToDate(String str){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(str,formatter);
        return Date.valueOf(date);
    }

    // GSON library to convert incoming data to JSON
    private <T> String convertToJson(T data){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(data);
    }

    // Currently not in use
    public boolean checkNotNullOrEmpty(String field){
        return !(field == null || field.equals(""));
    }

}
