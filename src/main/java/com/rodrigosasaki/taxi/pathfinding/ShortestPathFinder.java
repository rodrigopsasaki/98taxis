package com.rodrigosasaki.taxi.pathfinding;

import com.overload.algorithms.pathfinding.Pathfinder;
import com.overload.loc.Locatable;
import com.rodrigosasaki.taxi.model.Grid;
import com.rodrigosasaki.taxi.model.GridFlags;

import java.util.List;

public class ShortestPathFinder {

    private Pathfinder pathfinder;

    public ShortestPathFinder(Grid grid){
        pathfinder = new Pathfinder(Pathfinder.Algorithm.ASTAR, new GridFlags(grid));
    }

    public List<Locatable> shortestPath(Locatable a, Locatable b) {
        return pathfinder.findPath(a, b);
    }

}
