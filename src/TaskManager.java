import java.util.HashMap;
import java.util.Map;

public class TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }

    public void removeSubtask(int id) {
        subtasks.remove(id);
    }

    public void removeEpic(int id) {
        epics.remove(id);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateEpicStatus(Epic epic) {
        epic.updateStatus();
    }

    public void printTasks() {
        tasks.values().forEach(task -> System.out.println("Task ID: " + task.getId() + ", Status: " + task.getStatus()));
    }

    public void printSubtasks() {
        subtasks.values().forEach(subtask -> System.out.println("Subtask ID: " + subtask.getId() + ", Status: " + subtask.getStatus()));
    }

    public void printEpics() {
        epics.values().forEach(epic -> System.out.println("Epic ID: " + epic.getId() + ", Status: " + epic.getStatus()));
    }
}
