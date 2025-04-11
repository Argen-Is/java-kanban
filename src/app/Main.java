package app;

import manager.utils.Managers;
import manager.TaskManager;
import tasks.Task;
import tasks.TaskStatus;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Получаем TaskManager с внедрённым HistoryManager
        TaskManager taskManager = Managers.getDefault();

        // Создаём и добавляем задачи
        Task task1 = new Task("Task 1", "Description 1", TaskStatus.NEW);
        Task task2 = new Task("Task 2", "Description 2", TaskStatus.NEW);

        int task1Id = taskManager.addNewTask(task1);
        int task2Id = taskManager.addNewTask(task2);

        // Получаем задачи (это должно добавить их в историю)
        taskManager.getTask(task1Id);
        taskManager.getTask(task2Id);

        // Получаем историю
        List<Task> history = taskManager.getHistory();

        // Печатаем историю
        System.out.println("История просмотров:");
        for (Task task : history) {
            System.out.println(task);
        }
    }
}
