package com.rodrigosasaki.taxi.utils;

import com.overload.loc.Locatable;
import com.rodrigosasaki.taxi.agents.Passenger;
import com.rodrigosasaki.taxi.model.GridPosition;
import com.rodrigosasaki.taxi.stage.Stage;

import java.util.List;

public final class ValidationUtils {

    private ValidationUtils(){
        // utility class
    }

    public static String validCoordinates(Stage stage, int x, int y){
        GridPosition gridPosition = stage.getGrid().getPosition(x, y);
        if(gridPosition == null){
            return String.format(ErrorMessage.INEXISTENT_COORDINATE, x, y);
        } else if(!gridPosition.isWalkable()){
            return String.format(ErrorMessage.UNWALKABLE_COORDINATE, x, y);
        }
        return null;
    }

    public static String passengerRouteValid(Stage stage, Passenger passenger) {
        List<Locatable> path = PathUtils.shortestPath(stage.getGrid(), passenger.getLocation(), passenger.getDestination());
        if(path != null){
            passenger.setPath(path);
            return null;
        }
        return ErrorMessage.UNREACHABLE_DESTINATION;
    }

}
