import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDateTime;
import task.TaskStatus;
import task.Epic;
import task.Subtask;

class EpicTest {

    @Test
    void testEpicEndTime() {
        Epic epic = new Epic("Epic 1", "Epic description");

        Duration duration1 = Duration.ofHours(3);
        LocalDateTime startTime1 = LocalDateTime.of(2023, 10, 1, 12, 0);
        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", TaskStatus.NEW, 1);
        subtask1.setDuration(duration1);
        subtask1.setStartTime(startTime1);

        Duration duration2 = Duration.ofHours(1);
        LocalDateTime startTime2 = LocalDateTime.of(2023, 10, 2, 8, 0);
        Subtask subtask2 = new Subtask("Subtask 2", "Description 2", TaskStatus.NEW, 1);
        subtask2.setDuration(duration2);
        subtask2.setStartTime(startTime2);

        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);

        LocalDateTime expectedEndTime = startTime1.plus(duration1);
        assertEquals(expectedEndTime, epic.getEndTime());
    }
}