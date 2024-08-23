import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;

public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private int nextId = 1;

    public Collection<Task> getAllTasks() {
        return tasks.values();
    }

    public Collection<Epic> getAllEpics() {return epics.values(); }

    public Collection<Subtask> getAllSubtasks() {
        return subtasks.values();
    }

    public void deleteTasks() { tasks.clear(); }

    public void deleteEpics() {
        subtasks.clear();
        epics.clear();
        System.out.println("Все эпики и их подзадачи были удалены.");
    }

    public void deleteSubtasks() {
        for (Epic epic : epics.values()) {
            epic.clearSubtasks();
            epic.updateStatus();
        }
        subtasks.clear();
    }

    public void deleteAllTasks() { tasks.clear(); subtasks.clear(); epics.clear(); }

    public Task getTaskById(int id) { return tasks.get(id); }

    public Epic getEpicById(int id) { return epics.get(id); }

    public Subtask getSubtaskById(int id) { return subtasks.get(id); }

    public void createTask(Task task) {
        if (task.getId() <= 0) {
            task.setId(nextId);
            nextId++;
        }
        tasks.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        if (epic.getId() <= 0) {
            epic.setId(nextId);
            nextId++;
        }
        epics.put(epic.getId(), epic); }

    public void createSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getParentTaskID());
        if (epic != null) {
            if (subtask.getId() <= 0) {
                subtask.setId(nextId);
                nextId++;
            }
            epic.addSubtask(subtask);
            subtasks.put(subtask.getId(), subtask);
        } else {
            System.out.println("Не удалось создать подзадачу, так как эпик с ID " + subtask.getParentTaskID() + " не существует.");
        }
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
            System.out.println("Задача успешно обновлена");
        } else {
            System.out.println("Задача с ID " + task.getId() + " не существует.");
        }
    }

    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic existingEpic = epics.get(epic.getId());
            existingEpic.setTitle(epic.getTitle());
            existingEpic.setDescription(epic.getDescription());
        } else {
            System.out.println("Эпик с ID " + epic.getId() + " не существует.");
        }
    }

    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            Subtask existingSubtask = subtasks.get(subtask.getId());
            if (existingSubtask.getParentTaskID() == (subtask.getParentTaskID())) {
                subtasks.put(subtask.getId(), subtask);
                Epic epic = epics.get(subtask.getParentTaskID());
                if (epic != null) {
                    epic.updateStatus();
                }
            } else {
                System.out.println("ID не одинаковый. Невозможно обновить подзадачу.");
            }
        } else {
            System.out.println("Подзадача с ID " + subtask.getId() + " не существует. Обновление невозможно.");
        }
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            ArrayList<Subtask> subtasks = epic.getSubtasks();
            for (Subtask subtask : new ArrayList<>(subtasks)) {
                subtasks.remove(subtask);
            }
            epics.remove(id);
            System.out.println("Эпик с ID " + id + " и их подзадачи были удалены.");
        } else {
            System.out.println("Эпик с ID " + id + " не существует. Удаление невозможно.");
        }
    }

    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getParentTaskID());
            if (epic != null) {
                epic.removeSubtask(subtask);
                epic.updateStatus();
            }
            System.out.println("Подзадача с ID " + id + " была удалены.");
        } else {
            System.out.println("Подзадача с ID " + id + " не существует. Удаление невоможно.");
        }
    }

    public ArrayList<Subtask> getSubtasksOfEpic(int Id) {
        Epic epic = epics.get(Id);
        ArrayList<Subtask> epicSubtasks = new ArrayList<>();
        if (epic != null) {
            ArrayList<Subtask> subtasks = epic.getSubtasks();
            for (Subtask subtask : subtasks) {
                if (subtask != null) {
                    epicSubtasks.add(subtask);
                }
            }
        } else {
            System.out.println("Эпик ID " + Id + " не существует.");
        }
        return epicSubtasks;
    }
}

