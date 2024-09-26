package manager;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import task.Task;


public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Task> history = new LinkedHashMap<>();

    @Override
    public void add(Task task) {
        history.remove(task.getId());
        history.put(task.getId(), task);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history.values());
    }
}
