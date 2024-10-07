#!/bin/bash

# Остановка и удаление контейнера
echo "Остановка и удаление контейнера..."
docker stop simulation-world-2d
docker rm simulation-world-2d

# Удаление образа
echo "Удаление Docker-образа..."
docker rmi pokhodnya/simulation-world-2d:latest

echo "Удаление завершено."
