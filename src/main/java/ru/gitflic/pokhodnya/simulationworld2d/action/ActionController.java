package ru.gitflic.pokhodnya.simulationworld2d.action;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gitflic.pokhodnya.simulationworld2d.board.BoardService;
import ru.gitflic.pokhodnya.simulationworld2d.board.SymbolWithCoordinates;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class ActionController {

    private final BoardService boardService;
    private final ActionService actionService;

    @GetMapping("/turnActions")
    public ResponseEntity<List<SymbolWithCoordinates>> getSymbolCoordinates() {
        actionService.moveAllCreatures();
        List<SymbolWithCoordinates> coordinates = boardService.getSymbolCoordinates();
        return ResponseEntity.ok(coordinates);
    }

    @PostMapping("/initActions")
    public ResponseEntity<String> placeCreatures(@RequestParam int herbivoreCount, @RequestParam int predatorCount) {
        actionService.randomlyPlaceCreatures(herbivoreCount, predatorCount);
        return ResponseEntity.ok("Creatures placed successfully.");
    }
}
