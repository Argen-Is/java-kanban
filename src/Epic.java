import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks;

    public Epic(String name, String description, TaskStatus status) {
        super(name, description, status);
        this.subtasks = new ArrayList<>();
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void updateStatus() {
        if (subtasks.isEmpty()) {
            setStatus(TaskStatus.NEW);
        } else {
            boolean allDone = true;
            boolean anyInProgress = false;
            for (Subtask subtask : subtasks) {
                TaskStatus status = subtask.getStatus();
                if (status == TaskStatus.NEW || status == TaskStatus.IN_PROGRESS) {
                    anyInProgress = true;
                }
                if (status != TaskStatus.DONE) {
                    allDone = false;
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
    }
}
