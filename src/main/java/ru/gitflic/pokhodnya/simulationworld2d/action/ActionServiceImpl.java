package ru.gitflic.pokhodnya.simulationworld2d.action;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gitflic.pokhodnya.simulationworld2d.board.BoardService;
import ru.gitflic.pokhodnya.simulationworld2d.board.CoordinateDto;
import ru.gitflic.pokhodnya.simulationworld2d.entity.Carrot;
import ru.gitflic.pokhodnya.simulationworld2d.entity.Fox;
import ru.gitflic.pokhodnya.simulationworld2d.entity.Goat;
import ru.gitflic.pokhodnya.simulationworld2d.entity.Grass;
import ru.gitflic.pokhodnya.simulationworld2d.entity.Lettuce;
import ru.gitflic.pokhodnya.simulationworld2d.entity.Rabbit;
import ru.gitflic.pokhodnya.simulationworld2d.entity.Rock;
import ru.gitflic.pokhodnya.simulationworld2d.entity.Statue;
import ru.gitflic.pokhodnya.simulationworld2d.entity.Tree;
import ru.gitflic.pokhodnya.simulationworld2d.entity.Wolf;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Creature;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Entity;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Herbivore;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Obstacles;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Predator;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Resources;
import static ru.gitflic.pokhodnya.simulationworld2d.constant.MapDimensions.MAP_COLUMNS;
import static ru.gitflic.pokhodnya.simulationworld2d.constant.MapDimensions.MAP_ROWS;

import java.util.List;
import java.util.Random;

@Slf4j
@AllArgsConstructor
@Service
public class ActionServiceImpl implements ActionService {

    private final BoardService boardService;
    private final Random random = new Random();

    private final List<Class<? extends Herbivore>> herbivores = List.of(Goat.class, Rabbit.class);
    private final List<Class<? extends Predator>> predators = List.of(Fox.class, Wolf.class);
    private final List<Class<? extends Resources>> resources = List.of(Grass.class, Carrot.class, Lettuce.class);
    private final List<Class<? extends Obstacles>> obstacles = List.of(Tree.class, Rock.class, Statue.class);

    public void randomlyPlaceCreatures(int herbivoreCount, int predatorCount) {

        boardService.clearAllEntities();
        for (int i = 0; i < herbivoreCount; i++) {
            Class<? extends Herbivore> herbivoreClass = getRandomClass(herbivores);
            placeRandomEntity(createInstance(herbivoreClass));
        }

        for (int i = 0; i < predatorCount; i++) {
            Class<? extends Predator> predatorClass = getRandomClass(predators);
            placeRandomEntity(createInstance(predatorClass));
        }

        placeRandomResourcesAndObstacles(herbivoreCount, predatorCount);
    }

    private <T> Class<? extends T> getRandomClass(List<Class<? extends T>> classes) {
        return classes.get(random.nextInt(classes.size()));
    }

    private <T> T createInstance(Class<? extends T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Unable to create instance of class: " + clazz.getName(), e);
        }
    }

    private <T extends Entity> void placeRandomEntity(T entity) {
        CoordinateDto coordinate;
        do {
            int x = random.nextInt(MAP_COLUMNS);
            int y = random.nextInt(MAP_ROWS);
            coordinate = new CoordinateDto(x, y);
        } while (boardService.isCellOccupied(coordinate));

        boardService.addEntity(entity, coordinate);
    }

    private void placeRandomResourcesAndObstacles(int herbivoreCount, int predatorCount) {
        int totalCells = MAP_COLUMNS * MAP_ROWS;
        int occupiedCells = herbivoreCount + predatorCount;
        int unoccupiedCells = totalCells - occupiedCells;

        int resourcesCount = unoccupiedCells / 10;
        int obstaclesCount = unoccupiedCells / 10;

        for (int i = 0; i < resourcesCount; i++) {
            Class<? extends Resources> resourceClass = getRandomClass(resources);
            createAndPlace(resourceClass);
        }

        for (int i = 0; i < obstaclesCount; i++) {
            Class<? extends Obstacles> obstacleClass = getRandomClass(obstacles);
            createAndPlace(obstacleClass);
        }
    }
    private <T> void createAndPlace(Class<? extends T> clazz) {
        T entity = createInstance(clazz);
        placeRandomEntity((Entity) entity);
    }
    public void moveAllCreatures() {

        List<Creature> creatures = boardService.getAllEntities().stream()
                .filter(entity -> entity instanceof Creature)
                .map(entity -> (Creature) entity)
                .toList();

        for (Creature creature : creatures) {
            CoordinateDto currentCoordinates = boardService.getEntityCoordinates(creature);
            if (currentCoordinates == null) {
                log.warn("Creature {} has null coordinates, skipping movement.", creature);
                continue;
            }
            boardService.moveEntity(creature, creature.randomMove(currentCoordinates));
        }
    }
}
