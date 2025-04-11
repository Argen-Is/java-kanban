package manager.historyManager;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int MAX_HISTORY_SIZE = 10;
    private final LinkedHashMap<Integer, Task> history = new LinkedHashMap<>();

    @Override
    public void add(Task task) {
        // Если задача уже есть в истории, удаляем её и добавляем заново, чтобы переместить в конец
        history.remove(task.getId());
        history.put(task.getId(), task);

        // Если количество задач в истории превышает лимит, удаляем старую задачу
        if (history.size() > MAX_HISTORY_SIZE) {
            Iterator<Map.Entry<Integer, Task>> iterator = history.entrySet().iterator();
            iterator.next(); // Удаляем первую (самую старую) задачу
            iterator.remove();
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history.values());
    }
}
