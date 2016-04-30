package com.rodrigosasaki.taxi.model;

import com.overload.algorithms.pathfinding.Pathfinder;
import com.overload.loc.Locatable;
import com.overload.loc.Node;

public class GridFlags implements Pathfinder.Flags {

    private Grid grid;

    public GridFlags(Grid grid){
        this.grid = grid;
    }

    @Override
    public boolean blocked(Locatable loc, Locatable parent) {
        Position position = grid.getPosition(loc);
        return position == null || !grid.getPosition(loc).isWalkable();
    }

    public boolean blocked(int x, int y){
        return blocked(new Node(x, y), null);
    }

}
