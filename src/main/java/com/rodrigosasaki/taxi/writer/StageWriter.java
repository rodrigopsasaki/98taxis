package com.rodrigosasaki.taxi.writer;

import com.rodrigosasaki.taxi.model.GridPosition;
import com.rodrigosasaki.taxi.service.Grid;
import com.rodrigosasaki.taxi.stage.Stage;

import java.util.List;

public final class StageWriter {

    private StageWriter(){
        // utility class
    }

    public static String toHtml(Stage stage){
        char[][] matrix = transposeStage(stage);
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < matrix.length ; i++){
            for(int j = 0 ; j < matrix[i].length ; j++){
                sb.append(matrix[i][j]);
            }
            sb.append("<br/>");
        }

        return sb.toString();
    }

    public static String toString(Stage stage) {
        char[][] matrix = transposeStage(stage);
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < matrix.length ; i++){
            for(int j = 0 ; j < matrix[i].length ; j++){
                sb.append(matrix[i][j]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private static char[][] transposeStage(Stage stage) {
        Grid grid = stage.getGrid();
        List<GridPosition> positionsList = grid.getPositionsList();
        GridPosition highestGridPosition = grid.getHighestPosition();
        char[][] matrix = new char[highestGridPosition.getX() + 1][highestGridPosition.getY() + 1];

        for(GridPosition gridPosition : positionsList){
            matrix[gridPosition.getX()][gridPosition.getY()] = gridPosition.isWalkable() ? '_' : 'x';
        }

        stage.getPassengers().forEach(p -> matrix[p.getLocation().getX()][p.getLocation().getY()] = p.getState());
        stage.getTaxis().forEach(t -> matrix[t.getLocation().getX()][t.getLocation().getY()] = t.getState());

        return matrix;
    }

}
