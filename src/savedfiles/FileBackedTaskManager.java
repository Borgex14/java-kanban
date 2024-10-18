package savedfiles;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import manager.HistoryManager;
import manager.InMemoryTaskManager;
import task.Task;
import task.Subtask;
import task.Epic;


public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(HistoryManager historyManager, File file) {
        super(historyManager);
        this.file = file;
        loadFromFile();
    }

    private void loadFromFile() throws ManagerSaveException {
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                if (!line.trim().isEmpty()) {
                    Task task = Task.fromString(line);
                    if (task instanceof Epic) {
                        createEpic((Epic) task);
                    } else if (task instanceof Subtask) {
                        createSubtask((Subtask) task);
                    } else {
                        createTask(task);
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при загрузке данных из файла: " + file.getName(), e);
        } catch (Exception e) {
            throw new ManagerSaveException("Ошибка при обработке строки в файле: " + file.getName(), e);
        }
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public void deleteSubtasks() {
        super.deleteSubtasks();
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public Task createTask(Task task) {
        super.createTask(task);
        save();
        return task;
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public Task updateTask(Task task) {
        super.updateTask(task);
        save();
        return task;
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        save();
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Список сохраненных задач:\n");
            for (Task task : getAllTasks()) {
                writer.write(task.toString() + "\n");
            }
            for (Epic epic : getAllEpics()) {
                writer.write(epic.toString() + "\n");
            }
            for (Subtask subtask : getAllSubtasks()) {
                writer.write(subtask.toString() + "\n");
            }
            System.out.println("Задачи сохранены в файл: " + file.getName());
        } catch (IOException e) {
            throw new ManagerSaveException("При сохранении задач в файл произошла ошибка", e);
        }
    }
}












