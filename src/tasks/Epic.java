package tasks;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks = new ArrayList<>();

    public Epic(String name, String description, TaskStatus status) {
        super(name, description, status);
    }

    // Добавить подзадачу в эпик
    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    // Удалить подзадачу из эпика
    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    // Получить все подзадачи эпика
    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    // Обновить статус эпика в зависимости от статусов подзадач
    public void updateStatus() {
        boolean allDone = true;
        boolean anyInProgress = false;

        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() != TaskStatus.DONE) {
                allDone = false;
            }
            if (subtask.getStatus() == TaskStatus.IN_PROGRESS) {
                anyInProgress = true;
            }
        }

        if (allDone) {
            setStatus(TaskStatus.DONE);
        } else if (anyInProgress) {
            setStatus(TaskStatus.IN_PROGRESS);
        } else {
            setStatus(TaskStatus.NEW);
        }
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtasks=" + subtasks.size() +
                '}';
    }
}
