import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDateTime;
import task.Subtask;
import task.TaskStatus;

class SubtaskTest {

    @Test
    void testSubtaskDurationAndStartTime() {
        Duration duration = Duration.ofHours(2);
        LocalDateTime startTime = LocalDateTime.of(2023, 10, 1, 10, 0);
        Subtask subtask = new Subtask("Subtask 1", "Description", TaskStatus.NEW, 1);
        subtask.setDuration(duration);
        subtask.setStartTime(startTime);

        assertEquals(duration, subtask.getDuration());
        assertEquals(startTime, subtask.getStartTime());
    }

    @Test
    void testSubtaskEndTime() {
        Duration duration = Duration.ofHours(2);
        LocalDateTime startTime = LocalDateTime.of(2023, 10, 1, 10, 0);
        Subtask subtask = new Subtask("Subtask 1", "Description", TaskStatus.NEW, 1);
        subtask.setDuration(duration);
        subtask.setStartTime(startTime);

        LocalDateTime expectedEndTime = startTime.plus(duration);

        assertEquals(expectedEndTime, subtask.getEndTime());
    }

    @Test
    void testSubtaskEndTimeWithNull() {
        Subtask subtask = new Subtask("Subtask 1", "Description", TaskStatus.NEW, 1);
        assertNull(subtask.getEndTime());
        subtask.setDuration(Duration.ofHours(1));
        assertNull(subtask.getEndTime());
    }
}