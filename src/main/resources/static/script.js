let intervalId; // Переменная для хранения идентификатора интервала

document.getElementById('initializeButton').addEventListener('click', async () => {
    const herbivoreCount = document.getElementById('herbivoreCount').value;
    const predatorCount = document.getElementById('predatorCount').value;

    try {
        const response = await fetch(`api/creatures?herbivoreCount=${herbivoreCount}&predatorCount=${predatorCount}`, {
            method: 'POST',
        });

        if (!response.ok) {
            throw new Error(`HTTP ошибка! статус: ${response.status}`);
        }

        const message = await response.text();
        console.log(message); // Успешное сообщение
    } catch (error) {
        console.error('Ошибка при инициализации объектов:', error);
    }
    await performStep();
});
document.getElementById('stepButton').addEventListener('click', async () => {
    await performStep(); // Вызов функции для выполнения одного шага
});

document.getElementById('startButton').addEventListener('click', () => {
    // Если интервал уже запущен, ничего не делаем
    if (intervalId) {
        console.warn("Симуляция уже запущена");
        return;
    }

    // Запускаем интервал на 500 мс
    intervalId = setInterval(async () => {
        await performStep();
    }, 500);
});

document.getElementById('stopButton').addEventListener('click', () => {
    // Останавливаем интервал
    if (intervalId) {
        clearInterval(intervalId);
        intervalId = null; // Сбрасываем переменную идентификатора интервала
        console.log("Симуляция остановлена");
    }
});

async function performStep() {
    try {
        const response = await fetch('api/map');
        if (!response.ok) {
            throw new Error(`HTTP ошибка! статус: ${response.status}`);
        }

        const data = await response.json();
        console.log('Преобразованные данные:', data);

        if (Array.isArray(data)) {
            updateGrid(data);
        } else {
            console.error('Данные не являются массивом:', data);
        }
    } catch (error) {
        console.error('Ошибка при загрузке данных:', error);
    }
}

// Функция обновления сетки
function updateGrid(data) {
    const cells = document.querySelectorAll('td');
    cells.forEach(cell => {
        cell.textContent = "";
    });

    data.forEach(obj => {
        const row = obj.coordinates.y;
        const col = obj.coordinates.x;
        const cell = document.getElementById(`cell-${row}-${col}`);
        if (cell) {
            cell.textContent = obj.symbol; // Обновляем ячейку
        } else {
            console.warn(`Ячейка с id cell-${row}-${col} не найдена`);
        }
    });
}
