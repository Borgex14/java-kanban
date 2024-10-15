package task;

import savedfiles.TaskType;

public class Subtask extends Task {

    private String name;
    private String description;
    private int id;
    private TaskState state;
    private TaskType type;
    private final int parentTaskID;

    public Subtask(String title, String description, TaskState state, int parentTaskID) {
        super(title, state, description);
        this.parentTaskID = parentTaskID;
    }

    public Subtask(int id, TaskType type, String name, TaskState state, String description, int parentTaskID) {
        super(id,type, name, state, description);
        this.parentTaskID = parentTaskID;
    }

    public int getParentTaskID() {
        return parentTaskID;
    }

    public void setParentTaskID(int parentTaskID) {
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s,%d", id, type, name, state, description, parentTaskID);
    }

    public static Subtask fromString(String value) {
        String[] fields = value.split(",");
        int id = Integer.parseInt(fields[0]);
        TaskType type = TaskType.valueOf(fields[1]);
        String name = fields[2];
        TaskState state = TaskState.valueOf(fields[3]);
        String description = fields[4];
        int parentId = Integer.parseInt(fields[5]);
        Subtask subtask = new Subtask(id, type, name, state, description, parentId);
        return subtask;
    }
}
