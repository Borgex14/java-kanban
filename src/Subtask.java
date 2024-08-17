public class Subtask extends Task {
    private int parentTaskID; // Идентификатор родительской задачи

    public Subtask(String title, String description, int id, int parentTaskID) {
        super(title, description, id);
        this.parentTaskID = parentTaskID;
    }

    public int getParentTaskID() {
        return parentTaskID;
    }

    public void setParentTaskID(int parentTaskID) {
        this.parentTaskID = parentTaskID;
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
