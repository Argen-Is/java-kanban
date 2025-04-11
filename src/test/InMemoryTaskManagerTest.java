package test;

import manager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private InMemoryTaskManager manager;

    @BeforeEach
    void setUp() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void shouldNotAllowEpicAsItsOwnSubtask() {
        Epic epic = new Epic("Test Epic", "Test Description", TaskStatus.NEW);
        int epicId = manager.addNewEpic(epic);
        
        Subtask subtask = new Subtask("Test Subtask", "Test Description", TaskStatus.NEW, epicId);
        subtask.setId(epicId); // Пытаемся установить тот же id, что и у эпика
        
        assertThrows(IllegalArgumentException.class, () -> manager.addNewSubtask(subtask));
    }

    @Test
    void shouldNotAllowSubtaskAsItsOwnEpic() {
        Epic epic = new Epic("Test Epic", "Test Description", TaskStatus.NEW);
        int epicId = manager.addNewEpic(epic);
        
        Subtask subtask = new Subtask("Test Subtask", "Test Description", TaskStatus.NEW, epicId);
        int subtaskId = manager.addNewSubtask(subtask);
        
        assertThrows(IllegalArgumentException.class, () -> {
            Subtask anotherSubtask = new Subtask("Another Subtask", "Description", TaskStatus.NEW, subtaskId);
            manager.addNewSubtask(anotherSubtask);
        });
    }

    @Test
    void shouldAddAndRetrieveDifferentTaskTypes() {
        Task task = new Task("Test Task", "Description", TaskStatus.NEW);
        Epic epic = new Epic("Test Epic", "Description", TaskStatus.NEW);
        
        int taskId = manager.addNewTask(task);
        int epicId = manager.addNewEpic(epic);
        
        Subtask subtask = new Subtask("Test Subtask", "Description", TaskStatus.NEW, epicId);
        int subtaskId = manager.addNewSubtask(subtask);
        
        assertNotNull(manager.getTask(taskId));
        assertNotNull(manager.getEpic(epicId));
        assertNotNull(manager.getSubtask(subtaskId));
        
        assertEquals(1, manager.getTasks().size());
        assertEquals(1, manager.getEpics().size());
        assertEquals(1, manager.getSubtasks().size());
    }

    @Test
    void shouldNotModifyTaskWhenAdding() {
        Task originalTask = new Task("Test Task", "Description", TaskStatus.NEW);
        Task taskCopy = new Task("Test Task", "Description", TaskStatus.NEW);
        
        originalTask.setId(1);
        taskCopy.setId(1);
        
        manager.addNewTask(originalTask);
        
        assertEquals(taskCopy, originalTask, "Task should not be modified when added to manager");
        assertEquals(taskCopy.getStatus(), originalTask.getStatus(), "Task status should not change");
        assertEquals(taskCopy.getTitle(), originalTask.getTitle(), "Task title should not change");
        assertEquals(taskCopy.getDescription(), originalTask.getDescription(), "Task description should not change");
    }

    @Test
    void shouldHandleGeneratedAndCustomIds() {
        Task task1 = new Task("Task 1", "Description", TaskStatus.NEW);
        Task task2 = new Task("Task 2", "Description", TaskStatus.NEW);
        
        task1.setId(100); // Устанавливаем произвольный id
        int task1Id = manager.addNewTask(task1);
        int task2Id = manager.addNewTask(task2);
        
        assertEquals(100, task1Id, "Should preserve custom task id");
        assertNotEquals(100, task2Id, "Should generate different id for second task");
        
        Task retrievedTask1 = manager.getTask(task1Id);
        Task retrievedTask2 = manager.getTask(task2Id);
        
        assertNotNull(retrievedTask1);
        assertNotNull(retrievedTask2);
        assertNotEquals(retrievedTask1.getId(), retrievedTask2.getId());
    }

    @Test
    void shouldUpdateEpicStatusBasedOnSubtasks() {
        Epic epic = new Epic("Test Epic", "Description", TaskStatus.NEW);
        int epicId = manager.addNewEpic(epic);
        
        Subtask subtask1 = new Subtask("Subtask 1", "Description", TaskStatus.NEW, epicId);
        Subtask subtask2 = new Subtask("Subtask 2", "Description", TaskStatus.NEW, epicId);
        
        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        
        assertEquals(TaskStatus.NEW, manager.getEpic(epicId).getStatus(), "Epic should be NEW when all subtasks are NEW");
        
        subtask1.setStatus(TaskStatus.DONE);
        subtask2.setStatus(TaskStatus.IN_PROGRESS);
        
        manager.updateSubtask(subtask1); // Используем метод updateSubtask вместо addNewSubtask
        manager.updateSubtask(subtask2);
        
        assertEquals(TaskStatus.IN_PROGRESS, manager.getEpic(epicId).getStatus(), 
            "Epic should be IN_PROGRESS when subtasks have different statuses");
    }
}
