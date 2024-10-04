package ru.gitflic.pokhodnya.simulationworld2d.action;

public interface ActionService {
    void randomlyPlaceCreatures(int herbivoreCount, int predatorCount);
    void moveAllCreatures();
}
