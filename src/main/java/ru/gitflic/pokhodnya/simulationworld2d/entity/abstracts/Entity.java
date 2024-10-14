package ru.gitflic.pokhodnya.simulationworld2d.entity.abstracts;

import ru.gitflic.pokhodnya.simulationworld2d.entity.Emoji;

public interface Entity {

    default String getSymbol() {
        String className = this.getClass().getSimpleName().toUpperCase();
        try {
            return Emoji.valueOf(className).getSymbol();
        } catch (IllegalArgumentException e) {
            return "\uD83D\uDD37";
        }
    }
}
