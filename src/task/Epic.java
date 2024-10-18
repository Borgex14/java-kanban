package task;

import java.util.ArrayList;
import savedfiles.TaskType;


public class Epic extends Task {
    private final ArrayList<Subtask> subtasks;
    private String name;
    private String description;
    private int id;
    private TaskStatus status;
    private TaskType type;

    public Epic(String name, String description) {
        super(name, TaskStatus.NEW, description);
        this.subtasks = new ArrayList<>();
    }

    public Epic(int id, TaskType type, String name, TaskStatus status, String description) {
        super(id, type, name, TaskStatus.NEW, description);
        this.subtasks = new ArrayList<>();
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        updateStatus();
    }

    private void updateStatus() {
        if (subtasks.isEmpty()) {
            setStatus(TaskStatus.NEW);
        } else {
            boolean allDone = true;
            boolean anyInProgress = false;
            for (Subtask subtask : subtasks) {
                if (subtask.getStatus() != TaskStatus.DONE) {
                    allDone = false;
                }
                if (subtask.getStatus() == TaskStatus.IN_PROGRESS) {
                    anyInProgress = true;
                }
            }
            if (allDone) {
                setStatus(TaskStatus.DONE);
            } else if (anyInProgress) {
                setStatus(TaskStatus.IN_PROGRESS);
            } else {
                setStatus(TaskStatus.NEW);
            }
        }
    }

    public void setStatus(TaskStatus status) {
    }

    public void updateEpicStatus() {
        updateStatus();
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
        updateStatus();
    }

    public void clearSubtasks() {
        subtasks.clear();
        updateStatus();
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s", id, type, name, status, description);
    }

    public static Epic fromString(String value) {
        String[] fields = value.split(",");
        System.out.println(fields.length);
        int id = Integer.parseInt(fields[0]);
        TaskType type = TaskType.valueOf(fields[1]);
        String name = fields[2];
        TaskStatus status = TaskStatus.valueOf(fields[3]);
        String description = fields[4];
        return new Epic(id, type, name, status, description);
    }
}

