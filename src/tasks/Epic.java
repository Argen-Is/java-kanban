package tasks;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks = new ArrayList<>();

    public Epic(String name, String description, TaskStatus aNew) {
        super(name, description, TaskStatus.NEW); // Статус по умолчанию — NEW
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void clearSubtasks() {
        subtasks.clear();
        updateStatus();
    }

    public void updateStatus() {
        if (subtasks.isEmpty()) {
            setStatus(TaskStatus.NEW);
            return;
        }

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
