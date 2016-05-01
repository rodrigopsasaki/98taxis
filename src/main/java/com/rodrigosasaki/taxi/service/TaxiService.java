package com.rodrigosasaki.taxi.service;

import com.overload.loc.Locatable;
import com.rodrigosasaki.taxi.agents.Passenger;
import com.rodrigosasaki.taxi.agents.Taxi;
import com.rodrigosasaki.taxi.model.TaxiPath;
import com.rodrigosasaki.taxi.utils.PathUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("taxiService")
public class TaxiService {

    private static final Logger LOGGER = Logger.getLogger(TaxiService.class);

    @Autowired
    private Grid grid;

    private List<Taxi> taxis;

    public TaxiService(){
        taxis = new ArrayList<>();
    }

    public void assignTaxiToPassengers(List<Passenger> passengers) {
        passengers.stream()
                .filter(p -> !p.isAssigned())
                .forEach(p -> assignTaxiToPassenger(p));
    }

    public TaxiPath findClosestFreeTaxi(Locatable location) {
        return taxis.stream()
                .filter(t -> t.isFree())
                .map(t -> new TaxiPath(t, PathUtils.shortestPath(grid, t.getLocation(), location)))
                .filter(tp -> tp.getPath() != null)
                .min((t1, t2) -> t1.size() - t2.size())
                .orElse(null);
    }

    public void assignTaxiToPassenger(Passenger passenger) {
        Locatable location = passenger.getLocation();
        TaxiPath taxiPath = findClosestFreeTaxi(location);
        if(taxiPath != null) {
            Taxi taxi = taxiPath.getTaxi();
            taxi.pickupPassenger(passenger, taxiPath.getPath());
            passenger.assign();
        } else {
            LOGGER.info("There are no available taxis to perform this run");
        }
    }

    public void addTaxi(Taxi taxi){
        taxis.add(taxi);
    }

    public void setGrid(Grid grid){
        this.grid = grid;
    }

    public void performStep() {
        taxis.forEach(t -> t.performStep());
    }

    public List<Taxi> getTaxis(){
        return taxis;
    }

    public void restart() {
        taxis.clear();
    }
}
