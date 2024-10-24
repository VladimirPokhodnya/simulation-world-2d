package ru.gitflic.pokhodnya.simulationworld2d.board;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Entity;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Herbivore;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Obstacles;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Predator;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Resources;

import java.util.List;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

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

    @Override
    public CoordinateDto getEntityCoordinates(Entity entity) {
        return boardData.get(entity);
    }

    @Override
    public void moveEntity(Entity entity, CoordinateDto newCoordinates) {
        if (!boardData.contains(entity)) {
            return;
        }

        Entity entityAtNewCoordinates = getEntityAt(newCoordinates);

        if (entityAtNewCoordinates != null) {
            if (entityAtNewCoordinates instanceof Obstacles) {
                log.warn("Движение невозможно: препятствие на пути. Координаты: " + newCoordinates.x() + " " + newCoordinates.y());
                return;
            }

            if (entity instanceof Predator && entityAtNewCoordinates instanceof Herbivore) {
                log.info("Хищник встретил травоядное и съел его");
                boardData.remove(entityAtNewCoordinates);
                boardData.put(entity, newCoordinates);
                return;
            }

            if (entity instanceof Herbivore && entityAtNewCoordinates instanceof Predator) {
                log.info("Травоядное встретило хищника и было съедено");
                boardData.remove(entity);
                return;
            }

            if (entity instanceof Herbivore && entityAtNewCoordinates instanceof Resources) {
                log.info("Травоядное съело корм.");
                removeEntity(entityAtNewCoordinates);
                boardData.put(entity, newCoordinates);
                return;
            }

            if ((entity instanceof Herbivore && entityAtNewCoordinates instanceof Herbivore) ||
                (entity instanceof Predator && entityAtNewCoordinates instanceof Predator)) {
                log.warn("Движение невозможно: встреча с другой сущностью того же типа.");
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

    @Override
    public List<SymbolWithCoordinates> getSymbolCoordinates() {
        return boardData.getSymbolCoordinates();
    }

    @Override
    public List<Entity> getAllEntities() {
        return boardData.getAllEntities();
    }

    @Override
    public void clearAllEntities() {
        boardData.clearAllEntities();
    }
}

