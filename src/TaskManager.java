
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;

public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();        // Для обычных задач
    private final HashMap<Integer, Epic> epics = new HashMap<>();        // Для эпиков
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();  //  Для подзадач


    // Получение списка всех задач
    public Collection<Task> getAllTasks() {
        return tasks.values();
    }

    public Collection<Epic> getAllEpics() {return epics.values(); }

    public Collection<Subtask> getAllSubtasks() {
        return subtasks.values();
    }

    // Удаление задач
    public void deleteTasks() { tasks.clear(); }

    public void deleteEpics() { subtasks.clear(); }

    public void deleteSubtasks() { subtasks.clear(); }

    public void deleteAllTasks() { tasks.clear(); subtasks.clear(); epics.clear(); }

    // Получение задачи по идентификатору
    public Task getTaskById(int id) { return tasks.get(id); }

    public Epic getEpicById(int id) { return epics.get(id); }

    public Subtask getSubtaskById(int id) { return subtasks.get(id); }

    // Создание задачи
    public void createTask(Task task) { tasks.put(task.getId(), task);}

    public void createEpic(Epic epic) { epics.put(epic.getId(), epic); }


    public void createSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getParentTaskID());
        if (epic != null) {
            epic.addSubtask(subtask);
        }
    }

    // Обновление задачи
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        // Обновление статуса эпика, к которому относится подзадача
        Epic epic = epics.get(subtask.getParentTaskID());
        if (epic != null) {
            updateEpicStatus(epic);
        }
    }
    // Удаление задачи по идентификатору
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteEpicById(int id) {
        epics.remove(id);
    }

    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            // Обновление статуса эпика, к которому относится удаленная подзадача
            Epic epic = epics.get(subtask.getParentTaskID());
            if (epic != null) {
                updateEpicStatus(epic);
            }
        }
    }

    // Получение списка всех подзадач определённого эпика
    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        ArrayList<Subtask> epicSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getParentTaskID() == epicId) {
                epicSubtasks.add(subtask);
            }
        }
        return epicSubtasks;
    }

    // Обновление статуса эпика по подзадачам
    public void updateEpicStatus(Epic epic) {
        ArrayList<Subtask> epicSubtasks = getSubtasksOfEpic(epic.getId());
        if (epicSubtasks.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            boolean allDone = true;
            boolean allNew = true;

            for (Subtask subtask : epicSubtasks) {
                if (subtask.getStatus() != TaskStatus.DONE) {
                    allDone = false;
                }
                if (subtask.getStatus() != TaskStatus.NEW) {
                    allNew = false;
                }
            }

            if (allDone) {
                epic.setStatus(TaskStatus.DONE);
            } else if (allNew) {
                epic.setStatus(TaskStatus.NEW);
            } else {
                epic.setStatus(TaskStatus.IN_PROGRESS);
            }
        }
    }
}

