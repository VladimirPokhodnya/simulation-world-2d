package ru.gitflic.pokhodnya.simulationworld2d.board;

import org.springframework.stereotype.Component;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BoardData {
    private final Map<Entity, CoordinateDto> coordinateMap = new HashMap<>();

    public void put(Entity entity, CoordinateDto coordinates) {
        coordinateMap.put(entity, coordinates);
    }

    public CoordinateDto get(Entity entity) {
        return coordinateMap.get(entity);
    }

    public void remove(Entity entity) {
        coordinateMap.remove(entity);
    }

    public boolean contains(Entity entity) {
        return coordinateMap.containsKey(entity);
    }

    public boolean isCellOccupied(CoordinateDto coordinateDto) {
        return coordinateMap.values().stream()
                .anyMatch(coordinate -> coordinate.equals(coordinateDto));
    }

    public List<SymbolWithCoordinates> getSymbolCoordinates() {
        List<SymbolWithCoordinates> symbolCoordinatesList = new ArrayList<>();

        for (Entity entity : coordinateMap.keySet()) {
            CoordinateDto coords = coordinateMap.get(entity);
            symbolCoordinatesList.add(new SymbolWithCoordinates(entity.getSymbol(), coords));
        }

        return symbolCoordinatesList;
    }

    public List<Entity> getAllEntities() {
        return new ArrayList<>(coordinateMap.keySet());
    }

    public void clearAllEntities() {
        coordinateMap.clear();
    }

}





