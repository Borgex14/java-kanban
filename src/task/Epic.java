package task;

import java.util.ArrayList;
import savedfiles.TaskType;
import java.time.Duration;
import java.time.LocalDateTime;


public class Epic extends Task {
    private final ArrayList<Subtask> subtasks;
    private String name;
    private String description;
    private int id;
    private TaskStatus status;
    private TaskType type;
    private Duration duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, TaskStatus.NEW, description);
        this.subtasks = new ArrayList<>();
        this.duration = Duration.ZERO;
        this.startTime = null;
        this.endTime = null;
    }

    public Epic(int id, TaskType type, String name, TaskStatus status, String description) {
        super(id, type, name, TaskStatus.NEW, description);
        this.subtasks = new ArrayList<>();
        this.duration = Duration.ZERO;
        this.startTime = null;
        this.endTime = null;
    }

    public Epic(int id, TaskType type, String name, TaskStatus status, String description, Duration duration,
                LocalDateTime startTime, LocalDateTime endTime) {
        super(id, type, name, status, description);
        this.subtasks = new ArrayList<>();
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        updateStatus();
        calculateEpicDurationAndStartTime();
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

    public LocalDateTime getEndTime() {
        return endTime;
    }

    private void calculateEpicDurationAndStartTime() {
        this.duration = Duration.ZERO;
        this.startTime = null;
        this.endTime = null;

        for (Subtask subtask : subtasks) {
            this.duration = this.duration.plus(subtask.getDuration());

            if (this.startTime == null || (subtask.getStartTime() != null && subtask.getStartTime().isBefore(this.startTime))) {
                this.startTime = subtask.getStartTime();
            }

            if (this.endTime == null || (subtask.getStartTime() != null && getEndTime().isAfter(subtask.getEndTime()))) {
                this.endTime = subtask.getEndTime();
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
        return String.format("%d,%s,%s,%s,%s", id, type, name, status, description, duration.toMinutes(), startTime,
                endTime);
    }

    public static Epic fromString(String value) {
        String[] fields = value.split(",");
        System.out.println(fields.length);
        int id = Integer.parseInt(fields[0]);
        TaskType type = TaskType.valueOf(fields[1]);
        String name = fields[2];
        TaskStatus status = TaskStatus.valueOf(fields[3]);
        String description = fields[4];
        Duration duration = Duration.ofMinutes(Long.parseLong(fields[5]));
        LocalDateTime startTime = LocalDateTime.parse(fields[6]);
        LocalDateTime endTime = LocalDateTime.parse(fields[7]);
        return new Epic(id, type, name, status, description, duration, startTime, endTime);
    }
}

