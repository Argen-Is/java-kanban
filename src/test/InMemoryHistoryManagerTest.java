package test;

import manager.historyManager.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class InMemoryHistoryManagerTest {

    private InMemoryHistoryManager historyManager;

    @BeforeEach
    public void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    public void testAddToHistory() {
        Task task = new Task("Test task", "Description of task", TaskStatus.NEW);
        task.setId(1); // Устанавливаем id для задачи
        historyManager.add(task);
        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task, history.get(0));
    }

    @Test
    public void testHistorySizeLimit() {
        // Заполняем историю 11 задачами
        for (int i = 0; i < 10; i++) {
            Task task = new Task("Test task " + i, "Description of task " + i, TaskStatus.NEW);
            task.setId(i + 1); // Устанавливаем уникальный id для каждой задачи
            historyManager.add(task);
        }
        List<Task> history = historyManager.getHistory();
        assertEquals(10, history.size()); // История должна содержать не более 10 задач

        // Добавляем ещё одну задачу, должна удалиться самая старая
        Task newTask = new Task("Test task 11", "Description of task 11", TaskStatus.NEW);
        newTask.setId(11);
        historyManager.add(newTask);
        
        history = historyManager.getHistory();
        assertEquals(10, history.size()); // Размер не должен превышать 10
        assertFalse(history.stream().anyMatch(task -> task.getId() == 1), "Самая старая задача должна быть удалена");
        assertTrue(history.stream().anyMatch(task -> task.getId() == 11), "Новая задача должна быть добавлена");
    }
}
