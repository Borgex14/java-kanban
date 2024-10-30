package task;

import savedfiles.TaskType;
import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {

    private String name;
    private String description;
    private int id;
    private TaskStatus status;
    private TaskType type;
    private final int parentTaskID;
    private Duration duration;
    private LocalDateTime startTime;

    public Subtask(String title, String description, TaskStatus status, int parentTaskID) {
        super(title, status, description);
        this.parentTaskID = parentTaskID;
    }

    public Subtask(int id, TaskType type, String name, TaskStatus status, String description, int parentTaskID) {
        super(id,type, name, status, description);
        this.parentTaskID = parentTaskID;
    }

    public Subtask(int id, TaskType type, String name, TaskStatus status, String description, int parentTaskID, Duration duration, LocalDateTime startTime) {
        super(id, type, name, status, description);
        this.parentTaskID = parentTaskID;
        this.duration = duration;
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        if (startTime == null || duration == null) {
            return null;
        }
        return startTime.plus(duration);
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getParentTaskID() {
        return parentTaskID;
    }

    public void setParentTaskID(int parentTaskID) {
    }

    public void setStatus(TaskStatus status) {
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s,%d", id, type, name, status, description, parentTaskID,
                duration.toMinutes(), startTime);
    }

    public static Subtask fromString(String value) {
        String[] fields = value.split(",");
        int id = Integer.parseInt(fields[0]);
        TaskType type = TaskType.valueOf(fields[1]);
        String name = fields[2];
        TaskStatus status = TaskStatus.valueOf(fields[3]);
        String description = fields[4];
        int parentId = Integer.parseInt(fields[5]);
        Duration duration = Duration.ofMinutes(Long.parseLong(fields[6]));
        LocalDateTime startTime = LocalDateTime.parse(fields[7]);
        Subtask subtask = new Subtask(id, type, name, status, description, parentId, duration, startTime);
        return subtask;
    }
}
