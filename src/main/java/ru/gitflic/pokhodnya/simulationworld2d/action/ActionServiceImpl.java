package ru.gitflic.pokhodnya.simulationworld2d.action;

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
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.MapDimensions;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Obstacles;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Resources;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Herbivore;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Predator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

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
        // Получаем размеры карты из интерфейса MapDimensions
        int boardWidth = MapDimensions.MAP_COLUMNS;
        int boardHeight = MapDimensions.MAP_ROWS;

        // Генерируем травоядных
        for (int i = 0; i < herbivoreCount; i++) {
            Class<? extends Herbivore> herbivoreClass = getRandomClass(herbivores);
            placeRandomEntity(createInstance(herbivoreClass), boardWidth, boardHeight);
        }

        // Генерируем хищников
        for (int i = 0; i < predatorCount; i++) {
            Class<? extends Predator> predatorClass = getRandomClass(predators);
            placeRandomEntity(createInstance(predatorClass), boardWidth, boardHeight);
        }

        // Расстановка ресурсов и препятствий
        placeRandomResourcesAndObstacles(boardWidth, boardHeight);
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

    private <T extends Entity> void placeRandomEntity(T entity, int width, int height) {
        CoordinateDto coordinate;
        do {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            coordinate = new CoordinateDto(x, y);
        } while (boardService.isCellOccupied(coordinate)); // Проверка на занятость координат

        boardService.addEntity(entity, coordinate);
    }

    private void placeRandomResourcesAndObstacles(int width, int height) {
        for (int i = 0; i < 10; i++) { // Пример: 10 ресурсов
            Class<? extends Resources> resourceClass = getRandomClass(resources);
            createAndPlace(resourceClass, width, height);
        }
        for (int i = 0; i < 5; i++) { // Пример: 5 препятствий
            Class<? extends Obstacles> obstacleClass = getRandomClass(obstacles);
            createAndPlace(obstacleClass, width, height);
        }
    }

    private <T> void createAndPlace(Class<? extends T> clazz, int width, int height) {
        T entity = createInstance(clazz);
        placeRandomEntity((Entity) entity, width, height);
    }
    public void moveAllCreatures() {

        List<Creature> creatures = boardService.getAllEntities().stream()
                .filter(entity -> entity instanceof Creature)
                .map(entity -> (Creature) entity)
                .toList();

        for (Creature creature : creatures) {
            CoordinateDto currentCoordinates = boardService.getEntityCoordinates(creature);
            boardService.moveEntity(creature, creature.randomMove(currentCoordinates));
        }
    }
}