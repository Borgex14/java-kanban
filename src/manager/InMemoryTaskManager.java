package manager;

import java.util.Collections;
import java.util.stream.Collectors;
import task.Epic;
import task.Task;
import task.Subtask;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Objects;
import java.util.TreeSet;

public class InMemoryTaskManager implements TaskManager {
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, Subtask> subtasks = new HashMap<>();
    protected final HistoryManager historyManager = Managers.getDefaultHistoryManager();
    protected int nextId = 1;

    protected final TreeSet<Task> prioritizedTasks = new TreeSet<>((task1, task2) -> {
        if (task1.getStartTime() == null && task2.getStartTime() == null) return 0;
        if (task1.getStartTime() == null) return 1;
        if (task2.getStartTime() == null) return -1;
        return task1.getStartTime().compareTo(task2.getStartTime());
    });

    @Override
    public Collection<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    @Override
    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    @Override
    public Collection<Task> getAllTasks() {
        return tasks.values();
    }

    @Override
    public Collection<Epic> getAllEpics() {
        return epics.values();
    }

    @Override
    public Collection<Subtask> getAllSubtasks() {
        return subtasks.values();
    }

    @Override
    public void deleteTasks() {
        tasks.keySet().forEach(historyManager::remove);
        prioritizedTasks.removeIf(task -> tasks.containsKey(task.getId()));
        tasks.clear();
    }

    @Override
    public void deleteEpics() {
        epics.values().forEach(epic -> {
            epic.getSubtasks().forEach(subtask -> historyManager.remove(subtask.getId()));
            prioritizedTasks.removeIf(task -> subtasks.containsKey(task.getId()));
            historyManager.remove(epic.getId());
        });
        subtasks.clear();
        epics.clear();
        System.out.println("Все эпики и их подзадачи были удалены.");
    }

    @Override
    public void deleteSubtasks() {
        epics.values().forEach(Epic::clearSubtasks);
        subtasks.keySet().forEach(historyManager::remove);
        prioritizedTasks.removeIf(task -> subtasks.containsKey(task.getId()));
        subtasks.clear();
    }

    @Override
    public void deleteAllTasks() {
        for (Integer taskId : tasks.keySet()) {
            historyManager.remove(taskId);
        }
        for (Integer subtaskId : subtasks.keySet()) {
            historyManager.remove(subtaskId);
        }
        for (Integer epicId : epics.keySet()) {
            historyManager.remove(epicId);
        }
        prioritizedTasks.removeIf(task -> tasks.containsKey(task.getId()) || subtasks.containsKey(task.getId()));
        tasks.clear();
        subtasks.clear();
        epics.clear();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (Objects.nonNull(task)) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (Objects.nonNull(epic)) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (Objects.nonNull(subtask)) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public Task createTask(Task task) {
        if (task.getId() <= 0) {
            task.setId(nextId);
            nextId++;
        }
        boolean hasOverlap = getPrioritizedTasks().stream()
                .anyMatch(task::isOverlapping);
        if (hasOverlap) {
            throw new IllegalArgumentException("Задача пересекается с существующей задачей");
        }
        tasks.put(task.getId(), task);
        prioritizedTasks.add(task);
        return task;
    }

    @Override
    public void createEpic(Epic epic) {
        if (epic.getId() <= 0) {
            epic.setId(nextId);
            nextId++;
        }
        epics.put(epic.getId(), epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getParentTaskID());
        if (epic != null) {
            if (subtask.getId() <= 0) {
                subtask.setId(nextId);
                nextId++;
            }
            epic.addSubtask(subtask);
            boolean hasOverlap = getPrioritizedTasks().stream()
                    .anyMatch(subtask::isOverlapping);
            if (hasOverlap) {
                throw new IllegalArgumentException("Подзадача пересекается с существующей задачей");
            }
            subtasks.put(subtask.getId(), subtask);
            prioritizedTasks.add(subtask);
        } else {
            System.out.println("Не удалось создать подзадачу, так как эпик с ID " + subtask.getParentTaskID() + " не существует.");
        }
    }

    @Override
    public Task updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            for (Task existingTask : tasks.values()) {
                if (existingTask.getId() != task.getId() && existingTask.isOverlapping(task)) {
                    System.out.println("Обновление задачи невозможно: новое состояние пересекается с задачей с ID " + existingTask.getId());
                    return null;
                }
            }
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
            existingEpic.setName(epic.getName());
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
                for (Subtask existing : subtasks.values()) {
                    if (existing.getId() != subtask.getId() && existing.isOverlapping(subtask)) {
                        System.out.println("Обновление подзадачи невозможно: новое состояние пересекается с подзадачей с ID " + existing.getId());
                        return;
                    }
                }
                for (Task existing : tasks.values()) {
                    if (existing.isOverlapping(subtask)) {
                        System.out.println("Обновление подзадачи невозможно: новое состояние пересекается с задачей с ID " + existing.getId());
                        return;
                    }
                }
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
            epic.getSubtasks().forEach(subtask -> {
                historyManager.remove(subtask.getId());
            });
            epic.getSubtasks().clear();
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
    public List<Subtask> getSubtasksOfEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            return epic.getSubtasks().stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } else {
            System.out.println("Эпик ID " + id + " не существует.");
            return Collections.emptyList();
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}

