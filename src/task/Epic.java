package task;

import java.util.ArrayList;
import savedfiles.TaskType;


public class Epic extends Task {
    private final ArrayList<Subtask> subtasks;
    private String name;
    private String description;
    private int id;
    private TaskState state;
    private TaskType type;

    public Epic(String name, String description) {
        super(name, TaskState.NEW, description);
        this.subtasks = new ArrayList<>();
    }

    public Epic(int id, TaskType type, String name, TaskState state, String description) {
        super(id, type, name, TaskState.NEW, description);
        this.subtasks = new ArrayList<>();
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        updateState();
    }

    private void updateState() {
        if (subtasks.isEmpty()) {
            setState(TaskState.NEW);
        } else {
            boolean allDone = true;
            boolean anyInProgress = false;
            for (Subtask subtask : subtasks) {
                if (subtask.getState() != TaskState.DONE) {
                    allDone = false;
                }
                if (subtask.getState() == TaskState.IN_PROGRESS) {
                    anyInProgress = true;
                }
            }
            if (allDone) {
                setState(TaskState.DONE);
            } else if (anyInProgress) {
                setState(TaskState.IN_PROGRESS);
            } else {
                setState(TaskState.NEW);
            }
        }
    }

    private void setState(TaskState state) {
    }

    public void updateEpicState() {
        updateState();
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
        updateState();
    }

    public void clearSubtasks() {
        subtasks.clear();
        updateState();
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s", id, type, name, state, description);
    }

    public static Epic fromString(String value) {
        String[] fields = value.split(",");
        System.out.println(fields.length);
        int id = Integer.parseInt(fields[0]);
        TaskType type = TaskType.valueOf(fields[1]);
        String name = fields[2];
        TaskState state = TaskState.valueOf(fields[3]);
        String description = fields[4];
        return new Epic(id, type, name, state, description);
    }
}

