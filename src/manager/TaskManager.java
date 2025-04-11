package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public interface TaskManager {
    int addNewTask(Task task);
    Task getTask(int id);
    List<Task> getTasks();
    int addNewSubtask(Subtask subtask);
    Subtask getSubtask(int id);
    List<Subtask> getSubtasks();
    int addNewEpic(Epic epic);
    Epic getEpic(int id);
    List<Epic> getEpics();
    List<Subtask> getEpicSubtasks(int epicId);
    List<Task> getHistory();
    void updateTask(Task task);
    void updateSubtask(Subtask subtask);
    void updateEpic(Epic epic);
}
