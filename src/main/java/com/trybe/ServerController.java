package com.trybe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;



@RestController
public class ServerController {


    //*************************************
    // TEST SERVER FUNCTIONS
    //*************************************

    // Simple endpoint for connectivity test. Returns a string.
    @RequestMapping(value="/")
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


    // Accepts a JSON and produces a JSON
    @RequestMapping(value="/placeholder", method=RequestMethod.POST, produces="application/json")
    @ResponseBody
    public void exampleEndpoint(){
    }


    //*************************************
    // SERVER API FUNCTIONS
    //*************************************




    public static void main(String[] args) throws Exception {
        SpringApplication.run(ServerController.class, args);
    }

}
