package task;
import savedfiles.TaskType;
import java.time.Duration;
import java.time.LocalDateTime;

public class Task {
    private String name;
    private String description;
    private int id;
    private TaskStatus status;
    private TaskType type;
    private Duration duration;
    private LocalDateTime startTime;

    public Task(String name, TaskStatus status, String description) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(int id, TaskType type, String name, TaskStatus status, String description) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.type = type;
    }

    public Task(int id, TaskType type, String name, TaskStatus status, String description, Duration duration,
                LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.type = type;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String name, String description, int id, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskType getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Task)) return false;
        Task other = (Task) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s", id, type.name(), name, status.name(), description, duration.toMinutes(),
                startTime);
    }

    public static Task fromString(String value) {
        String[] fields = value.split(",");
        if (fields.length < 7) {
            throw new IllegalArgumentException("Неправильный формат строки для задачи: " + value);
        }
        int id = Integer.parseInt(fields[0]);
        TaskType type = TaskType.valueOf(fields[1]);
        String name = fields[2];
        TaskStatus status = TaskStatus.valueOf(fields[3]);
        String description = fields[4];
        Duration duration = Duration.ofMinutes(Long.parseLong(fields[5]));
        LocalDateTime startTime = LocalDateTime.parse(fields[6]);
        if (fields.length > 7 && type == TaskType.SUBTASK) {
            int parentId = Integer.parseInt(fields[7]);
            return new Subtask(id, type, name, status, description, parentId, duration, startTime);
        } else {
            if (type == TaskType.EPIC) {
                LocalDateTime endTime = LocalDateTime.parse(fields[7]);
                return new Epic(id, type, name, status, description, duration, startTime, endTime);
            } else {
                return new Task(id, type, name, status, description, duration, startTime);
            }
        }
    }
}

