package ru.gitflic.pokhodnya.simulationworld2d.action;

import ru.gitflic.pokhodnya.simulationworld2d.board.BoardService;
import ru.gitflic.pokhodnya.simulationworld2d.board.CoordinateDto;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Creature;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Entity;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Herbivore;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Predator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ActionServiceImpl implements ActionService {

    private final BoardService boardService;
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
