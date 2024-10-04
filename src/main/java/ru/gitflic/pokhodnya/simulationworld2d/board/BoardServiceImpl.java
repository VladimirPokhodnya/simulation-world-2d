package ru.gitflic.pokhodnya.simulationworld2d.board;

import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Entity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardData boardData;

    public BoardServiceImpl(BoardData boardData) {
        this.boardData = boardData;
    }

    public void addEntity(Entity entity, CoordinateDto coordinates) {
        boardData.put(entity, coordinates);
    }

    public boolean removeEntity(Entity entity) {
        if (boardData.contains(entity)) {
            boardData.remove(entity);
            return true;
        }
        return false;
    }

    public CoordinateDto getEntityCoordinates(Entity entity) {
        return boardData.get(entity);
    }

    public void moveEntity(Entity entity, CoordinateDto newCoordinates) {
        if (boardData.contains(entity)) {
            boardData.put(entity, newCoordinates);
        }
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

