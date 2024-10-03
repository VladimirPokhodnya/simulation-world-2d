document.getElementById('stepButton').addEventListener('click', async () => {
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
});

function updateGrid(data) {
    const cells = document.querySelectorAll('td');
    cells.forEach(cell => {
        cell.textContent = "";
        console.log("Ячейки очищены!");
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