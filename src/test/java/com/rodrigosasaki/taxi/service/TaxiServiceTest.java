package com.rodrigosasaki.taxi.service;

import com.overload.loc.Node;
import com.rodrigosasaki.taxi.agents.Passenger;
import com.rodrigosasaki.taxi.agents.Taxi;
import com.rodrigosasaki.taxi.agents.TaxiState;
import com.rodrigosasaki.taxi.model.TaxiPath;
import com.rodrigosasaki.taxi.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class TaxiServiceTest {

    @Autowired
    private TaxiService taxiService;

    @Autowired
    private Stage stage;

    @Before
    public void setup(){
        taxiService.restart();
    }

    @Test
    public void testAssignTaxiToPassengers(){
        Passenger passenger1 = new Passenger(new Node(0, 0), new Node(10, 10));
        Passenger passenger2 = new Passenger(new Node(3, 3), new Node(10, 10));
        Passenger passenger3 = new Passenger(new Node(5, 5), new Node(10, 10));
        List<Passenger> passengers = Arrays.asList(passenger1, passenger2, passenger3);

        Taxi taxi1 = new Taxi(new Node(9, 9), stage);
        Taxi taxi2 = new Taxi(new Node(10, 10), stage);

        taxiService.addTaxi(taxi1);
        taxiService.addTaxi(taxi2);
        taxiService.assignTaxiToPassengers(passengers);

        assertEquals(TaxiState.EN_ROUTE_TO_PASSENGER, taxi1.getTaxiState());
        assertEquals(TaxiState.EN_ROUTE_TO_PASSENGER, taxi2.getTaxiState());

        assertEquals(passenger1, taxi1.getPassenger());
        assertEquals(passenger2, taxi2.getPassenger());

        assertTrue(passenger1.isAssigned());
        assertTrue(passenger2.isAssigned());
        assertFalse(passenger3.isAssigned());
    }

    @Test
    public void testAssignClosestTaxi(){
        Passenger passenger = new Passenger(new Node(0, 0), new Node(10, 10));

        Taxi taxi1 = new Taxi(new Node(9, 9), stage);
        Taxi taxi2 = new Taxi(new Node(10, 10), stage);

        taxiService.addTaxi(taxi1);
        taxiService.addTaxi(taxi2);
        taxiService.assignTaxiToPassenger(passenger);

        assertEquals(TaxiState.EN_ROUTE_TO_PASSENGER, taxi1.getTaxiState());
        assertEquals(TaxiState.FREE, taxi2.getTaxiState());

        assertEquals(passenger, taxi1.getPassenger());
    }

    @Test
    public void testFindClosestFreeTaxi(){
        Taxi taxi1 = new Taxi(new Node(9, 9), stage);
        Taxi taxi2 = new Taxi(new Node(10, 10), stage);

        taxiService.addTaxi(taxi1);
        taxiService.addTaxi(taxi2);
        TaxiPath taxiPath = taxiService.findClosestFreeTaxi(new Node(5, 5));

        assertEquals(taxi1, taxiPath.getTaxi());
    }

    @Test
    public void testAssignsFirstIfSamePosition(){
        Taxi taxi1 = new Taxi(new Node(10, 10), stage);
        Taxi taxi2 = new Taxi(new Node(10, 10), stage);

        taxiService.addTaxi(taxi1);
        taxiService.addTaxi(taxi2);
        TaxiPath taxiPath = taxiService.findClosestFreeTaxi(new Node(5, 5));

        assertEquals(taxi1, taxiPath.getTaxi());
    }

}
