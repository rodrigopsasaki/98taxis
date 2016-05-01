package com.rodrigosasaki.taxi.parser;

import com.rodrigosasaki.taxi.model.GridPosition;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class CSVParser {

    private static final Logger LOGGER = Logger.getLogger(CSVParser.class);

    private CSVParser(){
        // utility class
    }

    public static List<GridPosition> defaultGridPositions(){
        try {
            Path csvPath = Paths.get(CSVParser.class.getClassLoader().getResource("defaultGrid.csv").toURI());
            return parseCSV(new String(Files.readAllBytes(csvPath)));
        } catch (IOException | URISyntaxException e) {
            LOGGER.error("There was an error reading the default grid csv file", e);
        }
        return null;
    }

    public static List<GridPosition> parseCSV(String csv) {
        List<GridPosition> gridPositions = new ArrayList<>();
        String[] lines = csv.split("\n");

        for(int i = 0 ; i < lines.length ; i++){
            String line = lines[i];
            String[] nodes = line.split(",", -1);
            for (int j = 0; j < nodes.length; j++) {
                String value = nodes[j];
                gridPositions.add(new GridPosition(i, j, isWalkable(value)));
            }
        }

        return gridPositions;
    }

    private static boolean isWalkable(String value) {
        return !"x".equalsIgnoreCase(value);
    }

}
