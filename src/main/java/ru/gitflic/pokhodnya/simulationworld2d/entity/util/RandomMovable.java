package ru.gitflic.pokhodnya.simulationworld2d.entity.util;

import ru.gitflic.pokhodnya.simulationworld2d.board.CoordinateDto;

import java.util.Random;

import static ru.gitflic.pokhodnya.simulationworld2d.constant.MapDimensions.MAP_COLUMNS;
import static ru.gitflic.pokhodnya.simulationworld2d.constant.MapDimensions.MAP_ROWS;

public interface RandomMovable {
    Random random = new Random();

    default CoordinateDto randomMove(CoordinateDto currentCoordinates) {
        if (currentCoordinates == null) {
            throw new IllegalArgumentException("currentCoordinates cannot be null");
        }
        int[][] directions = {
                {0, 1},
                {1, 1},
                {1, 0},
                {1, -1},
                {0, -1},
                {-1, -1},
                {-1, 0},
                {-1, 1}
        };

        int[] direction = directions[random.nextInt(directions.length)];

        int currentX = currentCoordinates.x();
        int currentY = currentCoordinates.y();

        int newX = (currentX + direction[0]) % MAP_COLUMNS;
        int newY = (currentY + direction[1]) % MAP_ROWS;

        if (newX < 0) newX += MAP_COLUMNS;
        if (newY < 0) newY += MAP_ROWS;

        return new CoordinateDto(newX, newY);
    }
}
