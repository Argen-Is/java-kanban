package app;

import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        // Создаем задачи
        Task task1 = new Task("Задача 1", "Описание задачи 1", TaskStatus.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", TaskStatus.NEW);
        manager.addTask(task1);
        manager.addTask(task2);

        // Создаем эпики
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1", TaskStatus.NEW);
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2", TaskStatus.NEW);
        manager.addEpic(epic1);
        manager.addEpic(epic2);

        // Создаем подзадачи
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", TaskStatus.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", TaskStatus.NEW, epic1.getId());
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", TaskStatus.NEW, epic2.getId());

        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addSubtask(subtask3);

        // Эпики уже содержат подзадачи благодаря добавлению в TaskManager
        // Эпик обновит свой статус автоматически, так как при добавлении подзадачи вызывается updateStatus()

        // Выводим все задачи, подзадачи и эпики
        System.out.println("Задачи:");
        manager.printTasks();
        System.out.println("Подзадачи:");
        manager.printSubtasks();
        System.out.println("Эпики:");
        manager.printEpics();

        // Обновляем статусы подзадач и эпиков
        subtask1.setStatus(TaskStatus.DONE);
        epic1.updateStatus(); // Обновляем статус эпика после изменения статуса подзадачи

        // Выводим после обновлений
        System.out.println("\nПосле изменений статусов:");
        manager.printTasks();
        manager.printSubtasks();
        manager.printEpics();

        // Удаляем задачу и эпик
        manager.removeTask(task1.getId());
        manager.removeEpic(epic2.getId());

        // Выводим после удаления
        System.out.println("\nПосле удаления:");
        manager.printTasks();
        manager.printEpics();

        // Удаляем все задачи и эпики
        manager.removeAllTasks();
        manager.removeAllEpics();

        // Выводим после удаления всех задач и эпиков
        System.out.println("\nПосле удаления всех задач и эпиков:");
        manager.printTasks();
        manager.printEpics();
    }
}
