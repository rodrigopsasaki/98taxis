package com.rodrigosasaki.taxi.stage;

import com.overload.loc.Locatable;
import com.rodrigosasaki.taxi.agents.Passenger;
import com.rodrigosasaki.taxi.agents.Taxi;
import com.rodrigosasaki.taxi.model.Grid;
import com.rodrigosasaki.taxi.model.TaxiPath;
import com.rodrigosasaki.taxi.parser.CSVParser;
import com.rodrigosasaki.taxi.pathfinding.ShortestPathFinder;
import com.rodrigosasaki.taxi.writer.StageWriter;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Stage {

    private static final Logger LOGGER = Logger.getLogger(Stage.class);

    private List<Taxi> taxis;
    private List<Passenger> passengers;
    private ShortestPathFinder pathFinder;
    private Grid grid;
    private StageWriter writer;

    public Stage() {
        this.writer = new StageWriter();
        init(null);
    }

    public void init(String gridString) {
        this.taxis = new ArrayList<>();
        this.passengers = new ArrayList<>();
        initializeGrid(gridString);
        this.pathFinder = new ShortestPathFinder(grid);
    }

    private void initializeGrid(String gridString){
        if(gridString == null || gridString.isEmpty()) {
            this.grid = CSVParser.defaultGrid();
        } else {
            this.grid = CSVParser.parseCSV(gridString);
        }
    }

    public void nextStep(){
        if(!passengers.isEmpty()){
            assignTaxiToPassengers();
        }

        taxis.forEach(t -> t.performStep());
    }

    private void assignTaxiToPassengers() {
        passengers = passengers.stream()
                .filter(p -> passengerRouteValid(p)).collect(Collectors.toList());

        passengers.stream()
                .filter(p -> !p.isAssigned())
                .forEach(p -> assignTaxiToPassenger(p));
    }

    private void assignTaxiToPassenger(Passenger passenger) {
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

    private boolean passengerRouteValid(Passenger passenger) {
        List<Locatable> path = pathFinder.shortestPath(passenger.getLocation(), passenger.getDestination());
        if(path != null){
            passenger.setPath(path);
            return true;
        }
        LOGGER.info("The destination the passenger is trying to reach is unreachable from where he currently is");
        return false;
    }

    private TaxiPath findClosestFreeTaxi(Locatable location) {
        return taxis.stream()
                .filter(t -> t.isFree())
                .map(t -> new TaxiPath(t, pathFinder.shortestPath(t.getLocation(), location)))
                .filter(tp -> tp.getPath() != null)
                .min((t1, t2) -> t1.size() - t2.size())
                .orElse(null);
    }

    public List<Taxi> getTaxis() {
        return taxis;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void addTaxi(Taxi taxi){
        taxis.add(taxi);
    }

    public void addPassenger(Passenger passenger){
        passengers.add(passenger);
    }

    public void removePassenger(Passenger passenger) {
        passengers.remove(passenger);
    }

    public Grid getGrid() {
        return grid;
    }

    @Override
    public String toString() {
        return writer.write(this);
    }

}
