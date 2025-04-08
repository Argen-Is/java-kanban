package manager;

import tasks.*;

import java.util.*;

public class TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.addSubtask(subtask);
            epic.updateStatus();
        }
    }

    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    // Получение задачи, подзадачи и эпика по ID
    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    // Удаление задачи, подзадачи и эпика по ID
    public void removeTask(int id) {
        tasks.remove(id);
    }

    public void removeSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtask(subtask);
                epic.updateStatus();
            }
        }
    }

    public void removeEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (Subtask subtask : epic.getSubtasks()) {
                subtasks.remove(subtask.getId());
            }
        }
    }

    // Удаление всех задач, подзадач и эпиков
    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.clearSubtasks(); // Очистка подзадач у эпика и обновление статуса
        }
        subtasks.clear();
    }

    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            for (Subtask subtask : epic.getSubtasks()) {
                subtasks.remove(subtask.getId());
            }
        }
        epics.clear();
    }

    // Получение коллекций задач, подзадач и эпиков
    public Collection<Task> getTasks() {
        return tasks.values();
    }

    public Collection<Subtask> getSubtasks() {
        return subtasks.values();
    }

    public Collection<Epic> getEpics() {
        return epics.values();
    }

    // Получение подзадач конкретного эпика
    public List<Subtask> getSubtasksOfEpic(int epicId) {
        List<Subtask> epicSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                epicSubtasks.add(subtask);
            }
        }
        return epicSubtasks;
    }

    // Вывод на консоль
    public void printTasks() {
        tasks.forEach((id, task) -> System.out.println(task));
    }

    public void printSubtasks() {
        subtasks.forEach((id, subtask) -> System.out.println(subtask));
    }

    public void printEpics() {
        epics.forEach((id, epic) -> System.out.println(epic));
    }
}
