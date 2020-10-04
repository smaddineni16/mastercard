package com.mastercard.navigation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/**
 *
 * @author Suresh Maddineni
 * @since 10/3/2020
 *
 */
@SpringBootTest
public class NavigationServiceTest {

    @Autowired
    NavigationService navigationService;


    @Test
    public void testConnectedRoad_Not_Adjacent_Cities() throws Exception {

        boolean result = navigationService.connectedRoad("New York", "Charlotte");
        Assert.isTrue(result, "The Cities have a connecting Road, though they are not adjacent");
    }

    @Test
    public void testConnectedRoad_Adjacent_Cities() throws Exception {

        boolean result = navigationService.connectedRoad("New York", "Washington DC");
        Assert.isTrue(result, "The Cities are adjacent to each other and has connecting road");
    }

    @Test
    public void testConnectedRoad_No_Connection_between_Cities() throws Exception {

        boolean result = navigationService.connectedRoad("trenton", "Charlotte");
        Assert.state(!result, "The Cities does not have a connecting road, so this should evaluate false");
    }

    @Test
    public void testConnectedRoad_No_Origin_City_Present() throws Exception {

        boolean result = navigationService.connectedRoad("Los Angeles", "Charlotte");
        Assert.state(!result, "The Starting City is not in the connected road list. So this should evaluate false");
    }

    @Test
    public void testConnectedRoad_No_Destination_City_Present() throws Exception {

        boolean result = navigationService.connectedRoad("New York", "Atlanta");
        Assert.state(!result, "The Destination City is not in the connected road list. So this should evaluate false");
    }

}
