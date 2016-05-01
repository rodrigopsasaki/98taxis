package com.rodrigosasaki.taxi.service;

import com.rodrigosasaki.taxi.agents.Passenger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service("passengerService")
public class PassengerService {

    private List<Passenger> passengers;

    @PostConstruct
    public void init(){
        passengers = new ArrayList<>();
    }

    public void addPassenger(Passenger passenger){
        passengers.add(passenger);
    }

    public void removePassenger(Passenger passenger){
        passengers.remove(passenger);
    }

    public boolean hasPassenger(){
        return !passengers.isEmpty();
    }

    public List<Passenger> getPassengers(){
        return passengers;
    }

    public void restart() {
        passengers.clear();
    }
}
