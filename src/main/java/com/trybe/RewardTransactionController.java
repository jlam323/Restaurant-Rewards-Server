package com.trybe;

import model.Points;
import model.RewardTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@ComponentScan
@RequestMapping("rewardtransaction")
public class RewardTransactionController {


    // Need to autowired or else the service will not be able to use the repositories
    @Autowired
    private ServerService ss;

    @RequestMapping(value="/find/{rtId}", method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> findRewardTransaction(@RequestParam String rtId){
        return ResponseEntity.status(200).body(ss.searchRewardTransaction(rtId));
    }

    @RequestMapping(value="/create", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<String> createRewardTransaction(@RequestBody RewardTransaction rt) {
        return ResponseEntity.status(200).body(
                ss.createRewardTransaction(rt.getCustomer(), rt.getId(), rt.getDate(), rt.getPointsCost()));
    }

    @RequestMapping(value="/update", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> updateRewardTransaction(@RequestBody RewardTransaction rt) {
        return ResponseEntity.status(200).body(
                ss.updateRewardTransaction(rt.getId(), rt.getDate(), rt.getPointsCost()));
    }

    @RequestMapping(value="/delete/{rtId}", method=RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deletePoints(@RequestParam String rtId){
        return ResponseEntity.status(200).body(ss.deleteRewardTransaction(rtId));
    }
}
