package com.rodrigosasaki.taxi.model;

import com.overload.loc.Locatable;

public class GridPosition implements Locatable, Comparable<GridPosition> {

    private int x;
    private int y;
    private boolean walkable;

    public GridPosition(int x, int y, boolean walkable){
        this.x = x;
        this.y = y;
        this.walkable = walkable;
    }

    public String getCoordinate(){
        return String.format("%02d,%02d", x, y);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public boolean isWalkable(){
        return walkable;
    }

    @Override
    public String toString() {
        return getCoordinate();
    }

    @Override
    public int compareTo(GridPosition other) {
        return this.getCoordinate().compareTo(other.getCoordinate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GridPosition)) return false;

        GridPosition gridPosition = (GridPosition) o;

        if (x != gridPosition.x) return false;
        return y == gridPosition.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
