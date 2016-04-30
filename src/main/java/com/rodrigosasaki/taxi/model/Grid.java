package com.rodrigosasaki.taxi.model;

import com.overload.loc.Locatable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Grid {

    private Map<String, Position> positionsMap;
    private List<Position> positionsList;

    public Grid(List<Position> positionsList){
        this.positionsList = positionsList;
        Collections.sort(positionsList);
        this.positionsMap = positionsList.stream()
                .collect(Collectors.toMap(Position::getCoordinate, Function.identity()));
    }

    public Position getPosition(Locatable loc){
        return getPosition(loc.getX(), loc.getY());
    }

    public Position getPosition(int x, int y){
        return getPosition(String.format("%02d,%02d", x, y));
    }

    public Position getPosition(String coordinate){
        return positionsMap.get(coordinate);
    }

    public Map<String, Position> getPositionsMap(){
        return positionsMap;
    }

    public int size(){
        return positionsMap.keySet().size();
    }

    public List<Position> getPositionsList() {
        return positionsList;
    }

    public Position getHighestPosition(){
        return positionsList.get(positionsList.size() - 1);
    }

}
