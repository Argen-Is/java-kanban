package manager;

import tasks.*;

import java.util.HashMap;
import java.util.Map;

public class TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();

    // Методы для добавления задач, подзадач и эпиков
    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.addSubtask(subtask);  // Добавляем подзадачу в эпик
            updateEpicStatus(epic);  // Обновляем статус эпика
        }
    }

    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    // Методы для удаления задач, подзадач и эпиков
    public void removeTask(int id) {
        tasks.remove(id);
    }

    public void removeSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtask(subtask);  // Удаляем подзадачу из эпика
                updateEpicStatus(epic);  // Обновляем статус эпика
            }
        }
    }

    public void removeEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (Subtask subtask : epic.getSubtasks()) {
                subtasks.remove(subtask.getId());  // Удаляем подзадачи эпика
            }
        }
    }

    // Методы для удаления всех задач, подзадач и эпиков
    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllSubtasks() {
        subtasks.clear();
    }

    public void removeAllEpics() {
        epics.clear();
    }

    // Методы для получения всех задач, подзадач и эпиков
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    public Map<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    // Метод для получения подзадач эпика
    public Map<Integer, Subtask> getSubtasksOfEpic(int epicId) {
        Map<Integer, Subtask> epicSubtasks = new HashMap<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                epicSubtasks.put(subtask.getId(), subtask);
            }
        }
        return epicSubtasks;
    }

    // Приватный метод для обновления статуса эпика
    private void updateEpicStatus(Epic epic) {
        epic.updateStatus();
    }

    // Вывод всех задач, подзадач и эпиков (оставлены для удобства тестирования)
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
