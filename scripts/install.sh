#!/bin/bash

# Проверка, установлен ли Docker
#if ! command -v docker &> /dev/null
#then
#    echo "Docker не установлен. Установка Docker..."
#    sudo apt-get update
#    sudo apt-get install -y docker.io
#    sudo systemctl start docker
#    sudo systemctl enable docker
#fi

# Скачивание образа из Docker Hub (замените my_image на нужное название образа)
IMAGE_NAME="pokhodnya/simulation-world-2d:latest"
echo "Скачивание образа $IMAGE_NAME из Docker Hub..."
docker pull $IMAGE_NAME

# Запуск контейнера
echo "Запуск контейнера..."
docker run -d --name simulation-world-2d -p 8080:8080 $IMAGE_NAME

echo "Установка завершена. Контейнер запущен!"
