public class Subtask extends Task {
    private Epic epic;

    public Subtask(String name, String description, TaskStatus status, Epic epic) {
        super(name, description, status);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }
}
