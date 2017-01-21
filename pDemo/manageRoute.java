package com.peoplenet.transportation.map.web.rest;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by mpodipireddy on 1/20/17.
 */
@RestController
@RequestMapping("/api")

public class manageRoute {

    @RequestMapping(value = "/manageMaps/steps",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> findSteps(@RequestBody String[] pRouteArray) throws URISyntaxException {


        Integer stepCount = 0;

        // concatinate array into single String which is easy to deal
        String completeRoute = String.join(".", pRouteArray);
        String actualRoute = null;

        if (completeRoute != null && !completeRoute.isEmpty()){

            int aIdx = completeRoute.indexOf("A");
            int bIdx = completeRoute.indexOf("B");

            //if A or B do not exit Input is valid
            if(aIdx == -1 || bIdx == -1 ) {
                return  new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
            }

            if (aIdx < bIdx) {
                actualRoute = completeRoute.substring(aIdx,bIdx+1);
            } else if (aIdx > bIdx) {
                actualRoute = completeRoute.substring(bIdx,aIdx+1);
            } else {
                //A and B are at same location there are no steps in between
                return  ResponseEntity.created(new URI("/manageMaps/steps"))
                        .body(stepCount);
            }

            // calculate steps noe
            if (actualRoute!= null) {
                stepCount = calculateStepCount(actualRoute);
            }
        } else {
            return  new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.created(new URI("/api/groups/" + pRouteArray.length))
                .body(stepCount);

    }

    private Integer calculateStepCount (String pActualroute) {

        //get number of occurences of .# to find steps
        int count = pActualroute.split("\\.#").length-1;

        if (pActualroute.indexOf("A") < pActualroute.indexOf("B")) {
            if (pActualroute.contains("#B")){
                // last blocked road at destination B should not be counted
                count = --count;
            }
        }
        else if (pActualroute.indexOf("A") > pActualroute.indexOf("B")){
            //last blocked road at destination B should not be counted
            if (pActualroute.contains("#A")){
                count = --count;
            }
        }
        return count;

    }

}
