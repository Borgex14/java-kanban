package task;

import savedfiles.TaskType;

public class Subtask extends Task {

    private String name;
    private String description;
    private int id;
    private TaskStatus status;
    private TaskType type;
    private final int parentTaskID;

    public Subtask(String title, String description, TaskStatus status, int parentTaskID) {
        super(title, status, description);
        this.parentTaskID = parentTaskID;
    }

    public Subtask(int id, TaskType type, String name, TaskStatus status, String description, int parentTaskID) {
        super(id,type, name, status, description);
        this.parentTaskID = parentTaskID;
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
        return String.format("%d,%s,%s,%s,%s,%d", id, type, name, status, description, parentTaskID);
    }

    public static Subtask fromString(String value) {
        String[] fields = value.split(",");
        int id = Integer.parseInt(fields[0]);
        TaskType type = TaskType.valueOf(fields[1]);
        String name = fields[2];
        TaskStatus status = TaskStatus.valueOf(fields[3]);
        String description = fields[4];
        int parentId = Integer.parseInt(fields[5]);
        Subtask subtask = new Subtask(id, type, name, status, description, parentId);
        return subtask;
    }
}
