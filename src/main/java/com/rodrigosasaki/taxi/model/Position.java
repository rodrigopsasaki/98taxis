package com.rodrigosasaki.taxi.model;

import com.overload.loc.Locatable;

public class Position implements Locatable, Comparable<Position> {

    private int x;
    private int y;
    private boolean walkable;

    public Position(int x, int y, boolean walkable){
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
    public int compareTo(Position other) {
        return this.getCoordinate().compareTo(other.getCoordinate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
