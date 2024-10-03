package ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts;

import ru.gitflic.pokhodnya.simulationworld2d.board.CoordinateDto;

import java.util.Random;

public interface Creature extends Entity{
    Random random = new Random();

    void makeMove();

    default CoordinateDto randomMove(CoordinateDto currentCoordinates) {
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
