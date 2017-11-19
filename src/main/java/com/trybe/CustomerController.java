package com.trybe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import model.*;


@RestController
@ComponentScan
@RequestMapping("customer")
public class CustomerController {


    // Need to autowired or else the service will not be able to use the repositories
    @Autowired
    private ServerService ss;

    @RequestMapping(value="/find/{custId}", method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> findCustomer(@RequestParam String custId){
        return ResponseEntity.status(200).body(ss.searchCustomer(custId));
    }

    @RequestMapping(value="/create", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.status(200).body(
                ss.createCustomer(customer.getId(), customer.getFirstName(), customer.getLastName(),
                        customer.getGender()));
    }

    @RequestMapping(value="/update", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer) {
        return ResponseEntity.status(200).body(
                ss.updateCustomer(customer.getId(), customer.getFirstName(), customer.getLastName(),
                        customer.getGender()));
    }

    @RequestMapping(value="/delete/{custId}", method=RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deleteCustomer(@RequestParam String custId){
        return ResponseEntity.status(200).body(ss.deleteCustomer(custId));
    }

}
