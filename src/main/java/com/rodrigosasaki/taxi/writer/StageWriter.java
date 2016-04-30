package com.rodrigosasaki.taxi.writer;

import com.rodrigosasaki.taxi.model.Grid;
import com.rodrigosasaki.taxi.model.Position;
import com.rodrigosasaki.taxi.stage.Stage;

import java.util.List;

public class StageWriter {

    public String write(Stage stage) {
        char[][] matrix = transposeGrid(stage.getGrid());
        stage.getPassengers().forEach(p -> matrix[p.getLocation().getX()][p.getLocation().getY()] = p.getState());
        stage.getTaxis().forEach(t -> matrix[t.getLocation().getX()][t.getLocation().getY()] = t.getState());

        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < matrix.length ; i++){
            for(int j = 0 ; j < matrix[i].length ; j++){
                sb.append(matrix[i][j]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private char[][] transposeGrid(Grid grid) {
        List<Position> positionsList = grid.getPositionsList();
        Position highestPosition = grid.getHighestPosition();
        char[][] matrix = new char[highestPosition.getX() + 1][highestPosition.getY() + 1];

        for(Position position : positionsList){
            matrix[position.getX()][position.getY()] = position.isWalkable() ? '_' : 'x';
        }

        return matrix;
    }

}
