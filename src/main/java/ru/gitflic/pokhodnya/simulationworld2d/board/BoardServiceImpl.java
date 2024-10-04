package ru.gitflic.pokhodnya.simulationworld2d.board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Entity;
import org.springframework.stereotype.Service;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Herbivore;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Obstacles;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Predator;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Resources;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);

    private final BoardData boardData;

    public BoardServiceImpl(BoardData boardData) {
        this.boardData = boardData;
    }

    public void addEntity(Entity entity, CoordinateDto coordinates) {
        boardData.put(entity, coordinates);
    }

    private void removeEntity(Entity entity) {
        if (boardData.contains(entity)) {
            boardData.remove(entity);
        }
    }

    public CoordinateDto getEntityCoordinates(Entity entity) {
        return boardData.get(entity);
    }

    public void moveEntity(Entity entity, CoordinateDto newCoordinates) {
        if (!boardData.contains(entity)) {
            return;
        }

        Entity entityAtNewCoordinates = getEntityAt(newCoordinates);

        if (entityAtNewCoordinates != null) {
            if (entityAtNewCoordinates instanceof Obstacles) {
                logger.warn("Движение невозможно: препятствие на пути.");
                return;
            }

            if (entity instanceof Predator && entityAtNewCoordinates instanceof Herbivore) {
                logger.info("Хищник съел травоядное.");
                boardData.remove(entityAtNewCoordinates);
                boardData.put(entity, newCoordinates);
                return;
            }

            if (entity instanceof Herbivore && entityAtNewCoordinates instanceof Resources) {
                logger.info("Травоядное съело корм.");
                removeEntity(entityAtNewCoordinates);
                boardData.put(entity, newCoordinates);
                return;
            }

            if ((entity instanceof Herbivore && entityAtNewCoordinates instanceof Herbivore) ||
                (entity instanceof Predator && entityAtNewCoordinates instanceof Predator)) {
                logger.warn("Движение невозможно: встреча с другой сущностью того же типа.");
                return;
            }
        }

        boardData.put(entity, newCoordinates);
    }

    private Entity getEntityAt(CoordinateDto coordinates) {
        return boardData.getAllEntities().stream()
                .filter(entity -> boardData.get(entity).equals(coordinates))
                .findFirst()
                .orElse(null);
    }


    public boolean isCellOccupied(CoordinateDto coordinateDto) {
        return boardData.isCellOccupied(coordinateDto);
    }

    public List<SymbolWithCoordinates> getSymbolCoordinates() {
        return boardData.getSymbolCoordinates();
    }

    public List<Entity> getAllEntities() {
        return boardData.getAllEntities();
    }

    public void clearAllEntities() {
        boardData.clearAllEntities();
    }
}

