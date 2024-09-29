package task;

public class Subtask extends Task {

    private final int parentTaskID;

    public Subtask(String title, String description, TaskStatus status, int parentTaskID) {
        super(title, description, status);
        this.parentTaskID = parentTaskID;
    }

    public Subtask(String title, String description, TaskStatus status, int id, int parentTaskID) {
        super(title, description, status, id);
        this.parentTaskID = parentTaskID;
    }

    public int getParentTaskID() {
        return parentTaskID;
    }


    public void setParentTaskID(int parentTaskID) {
    }

    @Override
    public String toString() {
        return "task.Subtask{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                ", parentTaskID=" + parentTaskID +
                '}';
    }
}
