package com.rodrigosasaki.taxi.parser;

import com.rodrigosasaki.taxi.service.Grid;
import org.junit.Test;

import static org.junit.Assert.*;

public class CSVParserTest {

    @Test
    public void testTwoByTwoGridWithoutBlocks(){
        String value = ",\n,";

        Grid grid = new Grid(CSVParser.parseCSV(value));

        assertTrue(grid.getPosition(0, 0).isWalkable());
        assertTrue(grid.getPosition(0, 1).isWalkable());
        assertTrue(grid.getPosition(1, 0).isWalkable());
        assertTrue(grid.getPosition(1, 1).isWalkable());
        assertEquals(4, grid.size());
    }

    @Test
    public void testTwoByTwoGridWithBlocks(){
        String value = ",\n,x";

        Grid grid = new Grid(CSVParser.parseCSV(value));

        assertTrue(grid.getPosition(0, 0).isWalkable());
        assertTrue(grid.getPosition(0, 1).isWalkable());
        assertTrue(grid.getPosition(1, 0).isWalkable());
        assertFalse(grid.getPosition(1, 1).isWalkable());
        assertEquals(4, grid.size());
    }

}
