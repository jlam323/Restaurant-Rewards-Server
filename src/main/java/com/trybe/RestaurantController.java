package com.trybe;

import model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@ComponentScan
@RequestMapping("restaurant")
public class RestaurantController {


    // Need to autowired or else the service will not be able to use the repositories
    @Autowired
    private ServerService ss;

    @RequestMapping(value="/find/{restId}", method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> findRestaurant(@RequestParam String restId){
        return ResponseEntity.status(200).body(ss.searchRestaurant(restId));
    }

    @RequestMapping(value="/create", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<String> createRestaurant(@RequestBody Restaurant restaurant) {
        return ResponseEntity.status(200).body(ss.createRestaurant(restaurant.getId(), restaurant.getName()));
    }

    @RequestMapping(value="/update", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> updateRestaurant(@RequestBody Restaurant restaurant) {
        return ResponseEntity.status(200).body(ss.updateRestaurant(restaurant.getId(), restaurant.getName()));
    }

    @RequestMapping(value="/delete/{restId}", method=RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deleteRestaurant(@RequestParam String restId){
        return ResponseEntity.status(200).body(ss.deleteRestaurant(restId));
    }
}
