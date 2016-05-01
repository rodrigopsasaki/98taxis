package com.rodrigosasaki.taxi.utils;

import com.overload.loc.Locatable;
import com.rodrigosasaki.taxi.pathfinding.ShortestPathFinder;
import com.rodrigosasaki.taxi.service.Grid;

import java.util.List;

public final class PathUtils {

    private PathUtils(){
        // utility class
    }

    public static List<Locatable> shortestPath(Grid grid, Locatable a, Locatable b){
        return new ShortestPathFinder(grid).shortestPath(a, b);
    }

}
