package tasks;

public class Subtask extends Task {
    private int epicId;

    public Subtask(int id, String taskTitle, String taskDescription, TaskStatus taskStatus, int epicId) {
        super(taskTitle, taskDescription, taskStatus);
        this.epicId = epicId;
    }

    public Subtask(String taskTitle, String taskDescription, TaskStatus taskStatus, int epicId) {
        super(taskTitle, taskDescription, taskStatus);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
