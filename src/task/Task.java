package task;
import savedfiles.TaskType;

public class Task {

    private String name;
    private String description;
    private int id;
    private TaskStatus status;
    private TaskType type;

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

    public Task(String name, String description, int id, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
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

    public void setStatus(TaskStatus Status) {
        this.status = Status;
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
        return String.format("%d,%s,%s,%s,%s", id, type.name(), name, status.name(), description);
    }

    public static Task fromString(String value) {
        String[] fields = value.split(",");
        if (fields.length < 5) {
            throw new IllegalArgumentException("Неправильный формат строки для задачи: " + value);
        }
        int id = Integer.parseInt(fields[0]);
        TaskType type = TaskType.valueOf(fields[1]);
        String name = fields[2];
        TaskStatus status = TaskStatus.valueOf(fields[3]);
        String description = fields[4];
        if (fields.length > 5 && type == TaskType.SUBTASK) {
            int parentId = Integer.parseInt(fields[5]);
            return new Subtask(id,type, name, status, description, parentId);
        } else {
            if (type == TaskType.EPIC) {
                return new Epic(id, type, name, status, description);
            } else {
                return new Task(id, type, name, status, description);
            }
        }
    }
}

