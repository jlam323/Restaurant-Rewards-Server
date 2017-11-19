package com.trybe;

import model.Reward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@ComponentScan
@RequestMapping("reward")
public class RewardController {


    // Need to autowired or else the service will not be able to use the repositories
    @Autowired
    private ServerService ss;

    @RequestMapping(value="/find/{rewardId}", method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> findReward(@RequestParam String rewardId){
        return ResponseEntity.status(200).body(ss.searchReward(rewardId));
    }

    @RequestMapping(value="/create", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<String> createReward(@RequestBody Reward reward) {
        return ResponseEntity.status(200).body(
                ss.createReward(reward.getRestaurant(), reward.getId(), reward.getText(), reward.getPointsCost()));
    }

    @RequestMapping(value="/update", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> updateRestaurant(@RequestBody Reward reward) {
        return ResponseEntity.status(200).body(
                ss.updateReward(reward.getId(), reward.getText(), reward.getPointsCost()));
    }

    @RequestMapping(value="/delete/{rewardId}", method=RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deleteReward(@RequestParam String rewardId){
        return ResponseEntity.status(200).body(ss.deleteReward(rewardId));
    }
}
