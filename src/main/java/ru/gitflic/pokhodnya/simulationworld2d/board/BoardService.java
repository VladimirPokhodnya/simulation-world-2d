package ru.gitflic.pokhodnya.simulationworld2d.board;

import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Entity;

import java.util.List;

public interface BoardService {
    void addEntity(Entity entity, CoordinateDto coordinateDto);
    boolean removeEntity(Entity entity);
    void moveEntity(Entity entity, CoordinateDto coordinateDto);
    CoordinateDto getEntityCoordinates(Entity entity);
    boolean isCellOccupied(CoordinateDto coordinateDto);
    List<SymbolWithCoordinates> getSymbolCoordinates();
    List<Entity> getAllEntities();
}

