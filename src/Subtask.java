public class Subtask extends Task {
    private final int parentTaskID; // Идентификатор родительской задачи

    // Конструктор с названием и описанием
    public Subtask(String title, String description, TaskStatus status, int parentTaskID) {
        super(title, description, status);
        this.parentTaskID = parentTaskID;
    }

    // Конструктор с ID, названием и описанием
    public Subtask(String title, String description, TaskStatus status, int id, int parentTaskID) {
        super(title, description, status, id);
        this.parentTaskID = parentTaskID;

    }
    public int getParentTaskID() {
        return parentTaskID;
    }

    public void setParentTaskID(Integer ParentTaskID) {
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                ", parentTaskID=" + parentTaskID +
                '}';
    }
}
