package ru.gitflic.pokhodnya.simulationworld2d.action;

import ru.gitflic.pokhodnya.simulationworld2d.board.BoardService;
import ru.gitflic.pokhodnya.simulationworld2d.board.CoordinateDto;
import ru.gitflic.pokhodnya.simulationworld2d.board.SymbolWithCoordinates;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Creature;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Entity;
import ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts.Predator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class ActionController {

    private final BoardService boardService;
    private final ActionService actionService;

    @GetMapping("/map")
    public ResponseEntity<List<SymbolWithCoordinates>> getSymbolCoordinates() {
        actionService.moveAllCreatures();
        List<SymbolWithCoordinates> coordinates = boardService.getSymbolCoordinates();
        return ResponseEntity.ok(coordinates);
    }



}
