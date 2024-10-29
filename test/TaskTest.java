import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDateTime;
import task.Task;
import task.TaskStatus;

class TaskTest {

    @Test
    void testTaskInitializationWithNewFields() {
        Task task = new Task("Task 1", TaskStatus.NEW, "Task Description");
        Duration duration = Duration.ofMinutes(30);
        LocalDateTime startTime = LocalDateTime.now();

        task.setDuration(duration);
        task.setStartTime(startTime);

        assertEquals(duration, task.getDuration());
        assertEquals(startTime, task.getStartTime());
    }

    @Test
    void testTaskEndTime() {
        Task task = new Task("Task 1", TaskStatus.NEW, "Task Description");
        Duration duration = Duration.ofMinutes(30);
        LocalDateTime startTime = LocalDateTime.now();

        task.setDuration(duration);
        task.setStartTime(startTime);

        LocalDateTime expectedEndTime = startTime.plus(duration);
        assertEquals(expectedEndTime, task.getEndTime());
    }
}