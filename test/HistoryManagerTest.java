import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Task;
import task.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryManagerTest {
    private HistoryManager historyManager;

    @BeforeEach
    public void setUp() {
        historyManager = new HistoryManager() {
            @Override
            public void add(Task task) {

            }

            @Override
            public List<Task> getHistory() {
                return List.of();
            }

            @Override
            public void remove(int id) {

            }
        };
    }

    @Test
    public void testEmptyHistory() {
        List<Task> history = historyManager.getHistory();
        assertTrue(history.isEmpty(), "History should be empty.");
    }
}