package manager;

import java.util.List;
import java.util.Collection;
import task.Task;
import task.Epic;
import task.Subtask;

public interface TaskManager {
    Collection<Task> getPrioritizedTasks();

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

    boolean deleteTaskById(int id);

    boolean deleteEpicById(int id);

    boolean deleteSubtaskById(int id);

    List<Subtask> getSubtasksOfEpic(int id);

    List<Task> getHistory();

    void setNextId(int nextId);
}
