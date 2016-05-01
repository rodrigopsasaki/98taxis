package com.rodrigosasaki.taxi.stage;

import com.rodrigosasaki.taxi.agents.Passenger;
import com.rodrigosasaki.taxi.agents.Taxi;
import com.rodrigosasaki.taxi.service.Grid;
import com.rodrigosasaki.taxi.service.PassengerService;
import com.rodrigosasaki.taxi.service.TaxiService;
import com.rodrigosasaki.taxi.writer.StageWriter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("stage")
public class Stage {

    private static final Logger LOGGER = Logger.getLogger(Stage.class);

    @Autowired
    private TaxiService taxiService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private Grid grid;

    public void nextStep(){
        if(passengerService.hasPassenger()){
            taxiService.assignTaxiToPassengers(passengerService.getPassengers());
        }
        taxiService.performStep();
    }

    public void restart(){
        taxiService.restart();
        passengerService.restart();
    }

    public List<Taxi> getTaxis() {
        return taxiService.getTaxis();
    }

    public List<Passenger> getPassengers() {
        return passengerService.getPassengers();
    }

    public void addTaxi(Taxi taxi){
        taxiService.addTaxi(taxi);
    }

    public void addPassenger(Passenger passenger){
        passengerService.addPassenger(passenger);
    }

    public void removePassenger(Passenger passenger) {
        passengerService.removePassenger(passenger);
    }

    public Grid getGrid() {
        return grid;
    }

    @Override
    public String toString() {
        return StageWriter.toString(this);
    }

    public String toHtml(){
        return StageWriter.toHtml(this);
    }

}
