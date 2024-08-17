import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks;  // // Для подзадач

    public Epic(String title, String description, int id) {
        super(title, description, id);
        this.subtasks = new ArrayList<>();
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);

    }

    @Override
    public String toString() {
        return "Epic{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                ", subtasks=" + getSubtasks() +
                '}';
    }
}
