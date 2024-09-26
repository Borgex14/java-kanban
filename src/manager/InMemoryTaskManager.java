package manager;

import task.Task;
import task.Subtask;
import task.Epic;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;


public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    private int nextId = 1;

    @Override
    public Collection<Task> getAllTasks() {
        return tasks.values();
    }

    @Override
    public Collection<Epic> getAllEpics() {return epics.values(); }

    @Override
    public Collection<Subtask> getAllSubtasks() {
        return subtasks.values();
    }

    @Override
    public void deleteTasks() { tasks.clear(); }

    @Override
    public void deleteEpics() {
        subtasks.clear();
        epics.clear();
        System.out.println("Все эпики и их подзадачи были удалены.");
    }

    @Override
    public void deleteSubtasks() {
        for (Epic epic : epics.values()) {
            epic.clearSubtasks();
        }
        subtasks.clear();
    }

    @Override
    public void deleteAllTasks() { tasks.clear(); subtasks.clear(); epics.clear(); }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (Objects.nonNull(task)){
            historyManager.add(task);
        }
        return tasks.get(id); }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (Objects.nonNull(epic)){
            historyManager.add(epic);
        }
        return epics.get(id); }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (Objects.nonNull(subtask)){
            historyManager.add(subtask);
        }
        return subtasks.get(id); }

    @Override
    public Task createTask(Task task) {
        if (task.getId() <= 0) {
            task.setId(nextId);
            nextId++;
        }
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public void createEpic(Epic epic) {
        if (epic.getId() <= 0) {
            epic.setId(nextId);
            nextId++;
        }
        epics.put(epic.getId(), epic); }

    @Override
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

    @Override
    public Task updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
            System.out.println("Задача успешно обновлена");
        } else {
            System.out.println("Задача с ID " + task.getId() + " не существует.");
        }
        return task;
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic existingEpic = epics.get(epic.getId());
            existingEpic.setTitle(epic.getTitle());
            existingEpic.setDescription(epic.getDescription());
        } else {
            System.out.println("Эпик с ID " + epic.getId() + " не существует.");
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            Subtask existingSubtask = subtasks.get(subtask.getId());
            if (existingSubtask.getParentTaskID() == (subtask.getParentTaskID())) {
                subtasks.put(subtask.getId(), subtask);
                Epic epic = epics.get(subtask.getParentTaskID());
                if (epic != null) {
                    epic.updateEpicStatus();
                }
            } else {
                System.out.println("ID не одинаковый. Невозможно обновить подзадачу.");
            }
        } else {
            System.out.println("Подзадача с ID " + subtask.getId() + " не существует. Обновление невозможно.");
        }
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            ArrayList<Subtask> subtasks = epic.getSubtasks();
            for (Subtask subtask : new ArrayList<>(subtasks)) {
                subtasks.remove(subtask);
            }
            epics.remove(id);
            historyManager.remove(id);
            System.out.println("Эпик с ID " + id + " и их подзадачи были удалены.");
        } else {
            System.out.println("Эпик с ID " + id + " не существует. Удаление невозможно.");
        }
    }

    @Override
    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.remove(id);
        historyManager.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getParentTaskID());
            if (epic != null) {
                epic.removeSubtask(subtask);
            }
            System.out.println("Подзадача с ID " + id + " была успешно удалена.");
        } else {
            System.out.println("Подзадача с ID " + id + " не существует. Удаление невоможно.");
        }
    }

    @Override
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

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}

