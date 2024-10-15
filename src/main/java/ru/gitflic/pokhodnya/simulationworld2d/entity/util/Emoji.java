package ru.gitflic.pokhodnya.simulationworld2d.entity.util;

public enum Emoji {
    ROCK("🪨"),
    TREE("🌳"),
    RABBIT("🐇"),
    GOAT("🐐"),
    WOLF("🐺"),
    FOX("🦊"),
    STATUE("🗿"),
    BRICK("🧱"),
    GRASS("🌱"),
    CARROT("🥕"),
    LETTUCE("🥬"),
    ENTITY("❓");

    private final String symbol;

    Emoji(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

}
