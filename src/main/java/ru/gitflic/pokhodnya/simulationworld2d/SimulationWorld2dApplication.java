package ru.gitflic.pokhodnya.simulationworld2d;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gitflic.pokhodnya.simulationworld2d.board.BoardService;
import ru.gitflic.pokhodnya.simulationworld2d.board.CoordinateDto;
import ru.gitflic.pokhodnya.simulationworld2d.entity.*;

@SpringBootApplication
public class SimulationWorld2dApplication implements CommandLineRunner {

    public SimulationWorld2dApplication(BoardService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        SpringApplication.run(SimulationWorld2dApplication.class, args);
    }

    private final BoardService service;
    @Override
    public void run(String... args) {
        for (Emoji emoji : Emoji.values()) {
            System.out.print(emoji.getSymbol());
        }
        System.out.println();

        service.addEntity(new Fox(), new CoordinateDto(2, 3));
        service.addEntity(new Wolf(), new CoordinateDto(3, 4));
        service.addEntity(new Goat(), new CoordinateDto(5, 6));
        service.addEntity(new Rabbit(), new CoordinateDto(7, 8));
        service.addEntity(new Grass(), new CoordinateDto(9, 9));
        service.addEntity(new Tree(), new CoordinateDto(10, 10));
        service.addEntity(new Rock(), new CoordinateDto(11, 11));


        System.out.println("Откройте в браузере ссылку http://localhost:8080/");
    }
}
