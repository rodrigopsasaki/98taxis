package com.rodrigosasaki.taxi.service;

import com.overload.loc.Locatable;
import com.rodrigosasaki.taxi.model.GridPosition;
import com.rodrigosasaki.taxi.parser.CSVParser;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("grid")
public class Grid {

    private Map<String, GridPosition> positionsMap;
    private List<GridPosition> positionsList;

    public Grid(){

    }

    public Grid(List<GridPosition> positionsList){
        this.positionsList = positionsList;
        Collections.sort(positionsList);
        this.positionsMap = positionsList.stream()
                .collect(Collectors.toMap(GridPosition::getCoordinate, Function.identity()));
    }

    @PostConstruct
    public void init(){
        positionsList = CSVParser.defaultGridPositions();
        Collections.sort(positionsList);
        this.positionsMap = positionsList.stream()
                .collect(Collectors.toMap(GridPosition::getCoordinate, Function.identity()));
    }

    public GridPosition getPosition(Locatable loc){
        return getPosition(loc.getX(), loc.getY());
    }

    public GridPosition getPosition(int x, int y){
        return getPosition(String.format("%02d,%02d", x, y));
    }

    public GridPosition getPosition(String coordinate){
        return positionsMap.get(coordinate);
    }

    public int size(){
        return positionsMap.keySet().size();
    }

    public List<GridPosition> getPositionsList() {
        return positionsList;
    }

    public GridPosition getHighestPosition(){
        return positionsList.get(positionsList.size() - 1);
    }

}
