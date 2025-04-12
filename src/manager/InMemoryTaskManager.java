package manager;

import manager.historyManager.HistoryManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;
import manager.utils.Managers;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();

    @Override
    public int addNewTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        
        // Генерируем новый id для задачи, если он не был установлен
        if (task.getId() == 0) {
            int newId = generateId();
            task.setId(newId);
        }
        
        tasks.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public int addNewSubtask(Subtask subtask) {
        if (subtask == null) {
            throw new IllegalArgumentException("Subtask cannot be null");
        }
        
        Epic epic = epics.get(subtask.getEpicId());
        if (epic == null) {
            throw new IllegalArgumentException("Epic with id " + subtask.getEpicId() + " not found");
        }
        
        // Генерируем новый id для подзадачи, если он не был установлен
        if (subtask.getId() == 0) {
            int newId = generateId();
            subtask.setId(newId);
        }
        
        // Проверка на циклические зависимости
        if (subtasks.containsKey(subtask.getEpicId())) {
            throw new IllegalArgumentException("Cannot add subtask with epicId pointing to another subtask");
        }
        
        // Проверка, не пытаемся ли мы использовать id эпика или существующей задачи для подзадачи
        if (epics.containsKey(subtask.getId()) || subtask.getId() == epic.getId() || tasks.containsKey(subtask.getId())) {
            throw new IllegalArgumentException("Cannot use existing task or epic id for subtask");
        }
        
        subtasks.put(subtask.getId(), subtask);
        epic.addSubtask(subtask);
        updateEpicStatus(epic);
        return subtask.getId();
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public int addNewEpic(Epic epic) {
        if (epic == null) {
            throw new IllegalArgumentException("Epic cannot be null");
        }
        
        // Генерируем новый id для эпика, если он не был установлен
        if (epic.getId() == 0) {
            int newId = generateId();
            epic.setId(newId);
        }
        
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic);
        return epic.getId();
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getEpicSubtasks(int epicId) {
        Epic epic = epics.get(epicId);
        return epic != null ? epic.getSubtasks() : new ArrayList<>();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void updateEpicStatus(Epic epic) {
        List<Subtask> epicSubtasks = epic.getSubtasks();
        if (epicSubtasks.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        boolean allDone = true;
        boolean allNew = true;

        for (Subtask subtask : epicSubtasks) {
            TaskStatus status = subtask.getStatus();
            if (status == TaskStatus.IN_PROGRESS) {
                epic.setStatus(TaskStatus.IN_PROGRESS);
                return;
            }
            if (status != TaskStatus.DONE) {
                allDone = false;
            }
            if (status != TaskStatus.NEW) {
                allNew = false;
            }
        }

        if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else if (allNew) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    @Override
    public void updateTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        if (!tasks.containsKey(task.getId())) {
            throw new IllegalArgumentException("Task with id " + task.getId() + " not found");
        }
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask == null) {
            throw new IllegalArgumentException("Subtask cannot be null");
        }
        if (!subtasks.containsKey(subtask.getId())) {
            throw new IllegalArgumentException("Subtask with id " + subtask.getId() + " not found");
        }
        Epic epic = epics.get(subtask.getEpicId());
        if (epic == null) {
            throw new IllegalArgumentException("Epic with id " + subtask.getEpicId() + " not found");
        }
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(epic);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epic == null) {
            throw new IllegalArgumentException("Epic cannot be null");
        }
        if (!epics.containsKey(epic.getId())) {
            throw new IllegalArgumentException("Epic with id " + epic.getId() + " not found");
        }
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic);
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtask(subtask);
                updateEpicStatus(epic);
            }
        }
    }

    @Override
    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (Subtask subtask : epic.getSubtasks()) {
                subtasks.remove(subtask.getId());
            }
        }
    }

    @Override
    public void deleteTasks() {
        tasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubtasks();
            updateEpicStatus(epic);
        }
    }

    @Override
    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    private int generateId() {
        int maxId = 0;
        for (int id : tasks.keySet()) {
            if (id > maxId) maxId = id;
        }
        for (int id : subtasks.keySet()) {
            if (id > maxId) maxId = id;
        }
        for (int id : epics.keySet()) {
            if (id > maxId) maxId = id;
        }
        return maxId + 1;
    }
}
