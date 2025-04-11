package test;

import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    @Test
    void shouldBeEmptySubtasksListByDefault() {
        Epic epic = new Epic("Test Epic", "Description", TaskStatus.NEW);
        assertTrue(epic.getSubtasks().isEmpty(), "Subtasks list should be empty by default");
    }

    @Test
    void shouldAddSubtask() {
        Epic epic = new Epic("Test Epic", "Description", TaskStatus.NEW);
        Subtask subtask = new Subtask("Test Subtask", "Description", TaskStatus.NEW, epic.getId());
        
        epic.addSubtask(subtask);
        
        assertEquals(1, epic.getSubtasks().size(), "Should have one subtask");
        assertTrue(epic.getSubtasks().contains(subtask), "Should contain added subtask");
    }

    @Test
    void shouldInheritTaskProperties() {
        String title = "Test Epic";
        String description = "Test Description";
        TaskStatus status = TaskStatus.NEW;
        
        Epic epic = new Epic(title, description, status);
        
        assertEquals(title, epic.getTitle(), "Title should match");
        assertEquals(description, epic.getDescription(), "Description should match");
        assertEquals(status, epic.getStatus(), "Status should match");
    }

    @Test
    void shouldBeEqualWhenSameId() {
        Epic epic1 = new Epic("Test Epic", "Description", TaskStatus.NEW);
        Epic epic2 = new Epic("Different Name", "Different Description", TaskStatus.IN_PROGRESS);
        
        epic1.setId(1);
        epic2.setId(1);
        
        assertEquals(epic1, epic2, "Epics with same ID should be equal");
    }

    @Test
    void shouldNotBeEqualWhenDifferentId() {
        Epic epic1 = new Epic("Test Epic", "Description", TaskStatus.NEW);
        Epic epic2 = new Epic("Test Epic", "Description", TaskStatus.NEW);
        
        epic1.setId(1);
        epic2.setId(2);
        
        assertNotEquals(epic1, epic2, "Epics with different IDs should not be equal");
    }

    @Test
    void shouldNotBeEqualToNull() {
        Epic epic = new Epic("Test Epic", "Description", TaskStatus.NEW);
        assertNotEquals(null, epic, "Epic should not be equal to null");
    }

    @Test
    void shouldHaveCorrectHashCodeWhenEqual() {
        Epic epic1 = new Epic("Test Epic", "Description", TaskStatus.NEW);
        Epic epic2 = new Epic("Different Name", "Different Description", TaskStatus.IN_PROGRESS);
        
        epic1.setId(1);
        epic2.setId(1);
        
        assertEquals(epic1.hashCode(), epic2.hashCode(), "Equal epics should have same hash code");
    }
}
