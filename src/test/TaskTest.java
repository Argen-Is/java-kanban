package test;

import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void shouldBeEqualWhenSameId() {
        Task task1 = new Task("Test Task", "Description", TaskStatus.NEW);
        Task task2 = new Task("Different Name", "Different Description", TaskStatus.IN_PROGRESS);
        
        task1.setId(1);
        task2.setId(1);
        
        assertEquals(task1, task2, "Tasks with same ID should be equal");
    }

    @Test
    void shouldNotBeEqualWhenDifferentId() {
        Task task1 = new Task("Test Task", "Description", TaskStatus.NEW);
        Task task2 = new Task("Test Task", "Description", TaskStatus.NEW);
        
        task1.setId(1);
        task2.setId(2);
        
        assertNotEquals(task1, task2, "Tasks with different IDs should not be equal");
    }

    @Test
    void shouldNotBeEqualToNull() {
        Task task = new Task("Test Task", "Description", TaskStatus.NEW);
        assertNotEquals(null, task, "Task should not be equal to null");
    }

    @Test
    void shouldHaveCorrectHashCodeWhenEqual() {
        Task task1 = new Task("Test Task", "Description", TaskStatus.NEW);
        Task task2 = new Task("Different Name", "Different Description", TaskStatus.IN_PROGRESS);
        
        task1.setId(1);
        task2.setId(1);
        
        assertEquals(task1.hashCode(), task2.hashCode(), "Equal tasks should have same hash code");
    }

    @Test
    void shouldPreservePropertiesAfterCreation() {
        String title = "Test Task";
        String description = "Test Description";
        TaskStatus status = TaskStatus.NEW;
        
        Task task = new Task(title, description, status);
        
        assertEquals(title, task.getTitle(), "Title should match");
        assertEquals(description, task.getDescription(), "Description should match");
        assertEquals(status, task.getStatus(), "Status should match");
    }

    @Test
    void shouldAllowStatusUpdate() {
        Task task = new Task("Test Task", "Description", TaskStatus.NEW);
        task.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus(), "Status should be updated");
    }

    @Test
    void shouldAllowIdUpdate() {
        Task task = new Task("Test Task", "Description", TaskStatus.NEW);
        task.setId(42);
        assertEquals(42, task.getId(), "ID should be updated");
    }
}
