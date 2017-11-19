package com.trybe;

import model.Points;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@ComponentScan
@RequestMapping("customer")
public class PointsController {


    // Need to autowired or else the service will not be able to use the repositories
    @Autowired
    private ServerService ss;

    @RequestMapping(value="/find/{pointsId}", method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> findPoints(@RequestParam String pointsId){
        return ResponseEntity.status(200).body(ss.searchPoints(pointsId));
    }

    @RequestMapping(value="/create", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<String> createPoints(@RequestBody Points points) {
        return ResponseEntity.status(200).body(
                ss.createPoints(points.getCustomer(), points.getId(), points.getAmount()));
    }

    @RequestMapping(value="/update", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> updatePoints(@RequestBody Points points) {
        return ResponseEntity.status(200).body(ss.updatePoints(points.getId(), points.getAmount()));
    }

    @RequestMapping(value="/delete/{pointsId}", method=RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deletePoints(@RequestParam String pointsId){
        return ResponseEntity.status(200).body(ss.deletePoints(pointsId));
    }

}
