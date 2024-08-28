import java.util.List;
import java.util.Collection;

public interface TaskManager {
    Collection<Task> getAllTasks();

    Collection<Epic> getAllEpics();

    Collection<Subtask> getAllSubtasks();

    void deleteTasks();

    void deleteEpics();

    void deleteSubtasks();

    void deleteAllTasks();

    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    Task createTask(Task task);

    void createEpic(Epic epic);

    void createSubtask(Subtask subtask);

    Task updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void deleteTaskById(int id);

    void deleteEpicById(int id);

    void deleteSubtaskById(int id);

    List<Subtask> getSubtasksOfEpic(int Id);

    List<Task> getHistory();
}
