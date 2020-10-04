package com.mastercard.navigation.controller;

import com.mastercard.navigation.service.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * This is the Controller class
 *
 * @author Suresh Maddineni
 * @since 10/3/2020
 *
 */
@RestController
public class NavigationController {

    @Autowired
    private NavigationService navigationService;
    
    @GetMapping(value = "/connected", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> areCitiesConnected(@RequestParam(name = "origin", defaultValue = "") String startingCity,
                                                     @RequestParam(name = "destination", defaultValue = "") String destinationCity) {

        // Call the Actual Service implementation
        return new ResponseEntity<>(navigationService.connectedRoad(startingCity, destinationCity)? "yes": "no", HttpStatus.OK);
    }


}
