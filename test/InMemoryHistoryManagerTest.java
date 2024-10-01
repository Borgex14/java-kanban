


import java.util.List;
import manager.InMemoryHistoryManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import task.Task;
import task.TaskStatus;


public class InMemoryHistoryManagerTest {
    private InMemoryHistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void testAddTask() {
        Task addedTask = new Task("задача 1", "Выполнить задачу 1", TaskStatus.NEW);

        historyManager.add(addedTask);

        List<Task> history = historyManager.getHistory();

        Assertions.assertEquals(addedTask, history.get(0));
    }

    @Test
    void testAddDuplicateTask() {
        Task task1 = new Task("Переезд", "Жить в другой город", TaskStatus.NEW);
        Task task2 = new Task("Переезд", "Обновленная информация", TaskStatus.NEW);

        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();

        Assertions.assertEquals(1, history.size());
        Assertions.assertEquals(task2, history.get(0));
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