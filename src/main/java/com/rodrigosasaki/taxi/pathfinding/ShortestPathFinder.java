package com.rodrigosasaki.taxi.pathfinding;

import com.overload.algorithms.pathfinding.Pathfinder;
import com.overload.loc.Locatable;
import com.rodrigosasaki.taxi.model.GridFlags;
import com.rodrigosasaki.taxi.service.Grid;

import java.util.List;

public class ShortestPathFinder {

    private Grid grid;

    public ShortestPathFinder(Grid grid){
        this.grid = grid;
    }

    public List<Locatable> shortestPath(Locatable a, Locatable b) {
        // create new one to avoid concurrency issues
        return new Pathfinder(Pathfinder.Algorithm.ASTAR, new GridFlags(grid)).findPath(a, b);
    }

}
