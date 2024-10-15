package task;
import savedfiles.TaskType;

public class Task {

    private String name;
    private String description;
    private int id;
    private TaskState state;
    private TaskType type;

    public Task(String name, TaskState state, String description) {
        this.name = name;
        this.description = description;
        this.state = state;
    }

    public Task(int id, TaskType type, String name, TaskState state, String description) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.state = state;
        this.type = type;
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

    public TaskState getState() {
        return state;
    }

    public void seState(TaskState state) {
        this.state = state;
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
        return String.format("%d,%s,%s,%s,%s", id, type, name, state, description);
    }

    public static Task fromString(String value) {
        String[] fields = value.split(",");
        int id = Integer.parseInt(fields[0]);
        TaskType type = TaskType.valueOf(fields[1]);
        String name = fields[2];
        TaskState state = TaskState.valueOf(fields[3]);
        String description = fields[4];
        if (fields.length > 5) {
            int parentId = Integer.parseInt(fields[5]);
            return new Subtask(id,type, name, state, description, parentId);
        } else {
            if (type == TaskType.EPIC) {
                return new Epic(id, type, name, state, description);
            } else {
                return new Task(id, type, name, state, description);
            }
        }
    }
}

