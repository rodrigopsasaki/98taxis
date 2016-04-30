package com.rodrigosasaki.taxi.agents;

import com.overload.loc.Locatable;
import com.overload.loc.Node;
import com.rodrigosasaki.taxi.stage.Stage;

import java.util.*;

public class Taxi implements Agent{

    private Locatable location;
    private TaxiState state;
    private Passenger passenger;
    private Queue<Locatable> path;
    private Stage stage;

    public Taxi(Locatable location, Stage stage){
        this.location = location;
        this.state = TaxiState.FREE;
        this.path = new LinkedList<>();
        this.stage = stage;
    }

    @Override
    public void performStep() {
        if(TaxiState.FREE.equals(state)){
            performFreeStep();
        } else if(TaxiState.EN_ROUTE_TO_PASSENGER.equals(state)){
            performEnRouteStep();
        } else if (TaxiState.OCCUPIED.equals(state)){
            performOccupiedStep();
        }
        move();
    }

    private void move() {
        if(!path.isEmpty()){
            location = path.poll();
        }
    }

    private void performFreeStep() {
        if(path.isEmpty()){
            moveToRandomNeighbor();
        }
    }

    private void moveToRandomNeighbor() {
        List<Locatable> positions = getShuffledNeighbors(location);

        for(Locatable position : positions){
            if(stage.getGrid().getPosition(position).isWalkable()){
                this.location = position;
                return;
            }
        }
    }

    private List<Locatable> getShuffledNeighbors(Locatable location) {
        List<Locatable> neighbours = new ArrayList<>();
        int x = location.getX();
        int y = location.getY();

        neighbours.add(new Node(x-1, y));
        neighbours.add(new Node(x+1, y));
        neighbours.add(new Node(x, y-1));
        neighbours.add(new Node(x, y+1));

        Collections.shuffle(neighbours);
        return neighbours;
    }


    private void performEnRouteStep() {
        if(location.equals(passenger.getLocation())){
            drivePassenger(passenger);
            stage.removePassenger(passenger);
        }
    }

    private void performOccupiedStep() {
        if(location.equals(passenger.getDestination())){
            dropoffPassenger();
        }
    }

    private void dropoffPassenger() {
        this.passenger = null;
        this.path.clear();
        this.state = TaxiState.FREE;
    }

    @Override
    public char getState() {
        return state.getValue();
    }

    public void pickupPassenger(Passenger passenger, List<Locatable> path){
        this.state = TaxiState.EN_ROUTE_TO_PASSENGER;
        this.passenger = passenger;
        this.path = new LinkedList<>(path);
    }

    public void drivePassenger(Passenger passenger){
        this.state = TaxiState.OCCUPIED;
        this.path = new LinkedList<>(passenger.getPath());
    }

    public boolean isFree(){
        return TaxiState.FREE.equals(state);
    }

    public Locatable getLocation(){
        return location;
    }

}
