package test;


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
}