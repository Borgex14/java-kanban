package tests;


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
    public void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    public void testAddTask() {
        Task task1 = new Task("задача 1", "Выполнить задачу 1", TaskStatus.NEW);

        historyManager.add(task1);

        List<Task> history = historyManager.getHistory();

        Assertions.assertEquals(task1, history.get(0));
    }
}