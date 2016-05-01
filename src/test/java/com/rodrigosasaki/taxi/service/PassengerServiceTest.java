package com.rodrigosasaki.taxi.service;

import com.overload.loc.Node;
import com.rodrigosasaki.taxi.agents.Passenger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class PassengerServiceTest {

    @Autowired
    private PassengerService passengerService;

    @Before
    public void setup(){
        passengerService.init();
    }

    @Test
    public void testAddPassenger(){
        passengerService.addPassenger(dummyPassenger());
        assertEquals(1, passengerService.getPassengers().size());
    }

    @Test
    public void testRemovePassengers(){
        Passenger passenger = dummyPassenger();
        passengerService.addPassenger(passenger);
        passengerService.addPassenger(dummyPassenger());
        assertEquals(2, passengerService.getPassengers().size());

        passengerService.removePassenger(passenger);
        assertEquals(1, passengerService.getPassengers().size());
    }

    @Test
    public void testRestart(){
        passengerService.addPassenger(dummyPassenger());
        assertEquals(1, passengerService.getPassengers().size());

        passengerService.restart();
        assertEquals(0, passengerService.getPassengers().size());
    }

    @Test
    public void testHasPassenger(){
        assertFalse(passengerService.hasPassenger());

        passengerService.addPassenger(dummyPassenger());
        assertTrue(passengerService.hasPassenger());

        passengerService.restart();
        assertFalse(passengerService.hasPassenger());
    }

    private Passenger dummyPassenger(){
        return new Passenger(new Node(0, 0), new Node(1, 1));
    }

}