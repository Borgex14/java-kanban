

import java.util.List;
import manager.InMemoryHistoryManager;
import org.junit.jupiter.api.Assertions;
import task.Task;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.TaskStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {
    private InMemoryHistoryManager historyManager;

    @BeforeEach
    public void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void testAddSameTask() {
        Task task1 = new Task("задача 1", "Выполнить задачу 1", TaskStatus.NEW);
        historyManager.add(task1);
        historyManager.add(task1);
        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task1, history.get(0));
    }

    @Test
    void testRemoveTask() {
        Task task1 = new Task("Переезд", "Жить в другой город", TaskStatus.NEW);
        historyManager.add(task1);
        historyManager.remove(task1.getId());
        List<Task> history = historyManager.getHistory();
        Assertions.assertEquals(0, history.size());
    }
}