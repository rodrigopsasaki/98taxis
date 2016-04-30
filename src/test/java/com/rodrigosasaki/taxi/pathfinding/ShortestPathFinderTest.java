package com.rodrigosasaki.taxi.pathfinding;

import com.overload.loc.Locatable;
import com.overload.loc.Node;
import com.rodrigosasaki.taxi.model.Grid;
import com.rodrigosasaki.taxi.parser.CSVParser;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ShortestPathFinderTest {

    @Test
    public void findPathInSingleLineWithoutBlocks(){
        Grid grid = CSVParser.parseCSV(",,");
        ShortestPathFinder pathfinder = new ShortestPathFinder(grid);

        List<Locatable> path = pathfinder.shortestPath(node(0, 0), node(0, 2));

        assertEquals(3, path.size());

        assertEquals(0, path.get(0).getX());
        assertEquals(0, path.get(0).getY());

        assertEquals(0, path.get(1).getX());
        assertEquals(1, path.get(1).getY());

        assertEquals(0, path.get(2).getX());
        assertEquals(2, path.get(2).getY());
    }

    @Test
    public void findPathInThreeByThreeWithoutBlocks(){
        Grid grid = CSVParser.parseCSV(",,\n,,\n,,");
        ShortestPathFinder pathfinder = new ShortestPathFinder(grid);

        List<Locatable> path = pathfinder.shortestPath(node(0, 0), node(2, 2));

        assertEquals(5, path.size());

        assertEquals(0, path.get(0).getX());
        assertEquals(0, path.get(0).getY());

        assertEquals(1, path.get(1).getX());
        assertEquals(0, path.get(1).getY());

        assertEquals(1, path.get(2).getX());
        assertEquals(1, path.get(2).getY());

        assertEquals(1, path.get(3).getX());
        assertEquals(2, path.get(3).getY());

        assertEquals(2, path.get(4).getX());
        assertEquals(2, path.get(4).getY());
    }

    @Test
    public void findPathInThreeByThreeWithBlocks(){
        Grid grid = CSVParser.parseCSV(",,x\n,x,x\n,,");
        ShortestPathFinder pathfinder = new ShortestPathFinder(grid);

        List<Locatable> path = pathfinder.shortestPath(node(0, 0), node(2, 2));

        assertEquals(5, path.size());

        assertEquals(0, path.get(0).getX());
        assertEquals(0, path.get(0).getY());

        assertEquals(1, path.get(1).getX());
        assertEquals(0, path.get(1).getY());

        assertEquals(2, path.get(2).getX());
        assertEquals(0, path.get(2).getY());

        assertEquals(2, path.get(3).getX());
        assertEquals(1, path.get(3).getY());

        assertEquals(2, path.get(4).getX());
        assertEquals(2, path.get(4).getY());
    }

    @Test
    public void findPathWithUnwalkableTarget(){
        Grid grid = CSVParser.parseCSV(",,\n,,\n,,x");
        ShortestPathFinder pathfinder = new ShortestPathFinder(grid);

        List<Locatable> path = pathfinder.shortestPath(node(0, 0), node(2, 2));

        assertNull(path);
    }

    @Test
    public void findPathWithUnpassableObstacle(){
        Grid grid = CSVParser.parseCSV(",,x\n,x,\nx,,");
        ShortestPathFinder pathfinder = new ShortestPathFinder(grid);

        List<Locatable> path = pathfinder.shortestPath(node(0, 0), node(2, 2));

        assertNull(path);
    }

    private Node node(int x, int y){
        return new Node(x, y);
    }

}