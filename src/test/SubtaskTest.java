package test;

import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    @Test
    void shouldHaveEpicId() {
        Epic epic = new Epic("Test Epic", "Description", TaskStatus.NEW);
        epic.setId(1);
        
        Subtask subtask = new Subtask("Test Subtask", "Description", TaskStatus.NEW, epic.getId());
        
        assertEquals(epic.getId(), subtask.getEpicId(), "Subtask should have correct epic ID");
    }

    @Test
    void shouldInheritTaskProperties() {
        String title = "Test Subtask";
        String description = "Test Description";
        TaskStatus status = TaskStatus.NEW;
        int epicId = 1;
        
        Subtask subtask = new Subtask(title, description, status, epicId);
        
        assertEquals(title, subtask.getTitle(), "Title should match");
        assertEquals(description, subtask.getDescription(), "Description should match");
        assertEquals(status, subtask.getStatus(), "Status should match");
        assertEquals(epicId, subtask.getEpicId(), "Epic ID should match");
    }

    @Test
    void shouldBeEqualWhenSameId() {
        Subtask subtask1 = new Subtask("Test Subtask", "Description", TaskStatus.NEW, 1);
        Subtask subtask2 = new Subtask("Different Name", "Different Description", TaskStatus.IN_PROGRESS, 2);
        
        subtask1.setId(1);
        subtask2.setId(1);
        
        assertEquals(subtask1, subtask2, "Subtasks with same ID should be equal");
    }

    @Test
    void shouldNotBeEqualWhenDifferentId() {
        Subtask subtask1 = new Subtask("Test Subtask", "Description", TaskStatus.NEW, 1);
        Subtask subtask2 = new Subtask("Test Subtask", "Description", TaskStatus.NEW, 1);
        
        subtask1.setId(1);
        subtask2.setId(2);
        
        assertNotEquals(subtask1, subtask2, "Subtasks with different IDs should not be equal");
    }

    @Test
    void shouldNotBeEqualToNull() {
        Subtask subtask = new Subtask("Test Subtask", "Description", TaskStatus.NEW, 1);
        assertNotEquals(null, subtask, "Subtask should not be equal to null");
    }

    @Test
    void shouldHaveCorrectHashCodeWhenEqual() {
        Subtask subtask1 = new Subtask("Test Subtask", "Description", TaskStatus.NEW, 1);
        Subtask subtask2 = new Subtask("Different Name", "Different Description", TaskStatus.IN_PROGRESS, 2);
        
        subtask1.setId(1);
        subtask2.setId(1);
        
        assertEquals(subtask1.hashCode(), subtask2.hashCode(), "Equal subtasks should have same hash code");
    }

    @Test
    void shouldAllowStatusUpdate() {
        Subtask subtask = new Subtask("Test Subtask", "Description", TaskStatus.NEW, 1);
        subtask.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, subtask.getStatus(), "Status should be updated");
    }
}
