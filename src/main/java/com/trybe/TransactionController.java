package com.trybe;

import model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@ComponentScan
@RequestMapping("customer")
public class TransactionController {


    // Need to autowired or else the service will not be able to use the repositories
    @Autowired
    private ServerService ss;

    @RequestMapping(value="/find/{transId}", method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> findTransaction(@RequestParam String transId){
        return ResponseEntity.status(200).body(ss.searchTransaction(transId));
    }

    @RequestMapping(value="/create", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<String> createTransaction(@RequestBody Transaction trans) {
        return ResponseEntity.status(200).body(
                ss.createTransaction(trans.getCustomer(), trans.getRestaurant(), trans.getId(), trans.getDate(),
                        trans.getAmount()));
    }

    @RequestMapping(value="/update", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> updateTransction(@RequestBody Transaction trans) {
        return ResponseEntity.status(200).body(ss.updateTransaction(trans.getId(), trans.getDate(), trans.getAmount()));
    }

    @RequestMapping(value="/delete/{transId}", method=RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deleteTransaction(@RequestParam String transId){
        return ResponseEntity.status(200).body(ss.deleteTransaction(transId));
    }

}
