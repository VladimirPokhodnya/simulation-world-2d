package ru.gitflic.pokhodnya.simulationworld2d;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gitflic.pokhodnya.simulationworld2d.action.ActionService;
import ru.gitflic.pokhodnya.simulationworld2d.board.BoardService;
import ru.gitflic.pokhodnya.simulationworld2d.board.CoordinateDto;
import ru.gitflic.pokhodnya.simulationworld2d.entity.*;

@SpringBootApplication
public class SimulationWorld2dApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(SimulationWorld2dApplication.class, args);
    }


    @Override
    public void run(String... args) {
        for (Emoji emoji : Emoji.values()) {
            System.out.print(emoji.getSymbol());
        }
        System.out.println();

        System.out.println("Откройте в браузере ссылку http://localhost:8080/");
    }
}
