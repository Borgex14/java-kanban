
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;

public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();        // Для обычных задач
    private final HashMap<Integer, Epic> epics = new HashMap<>();        // Для эпиков
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();  //  Для подзадач

    // Счетчик для генерации уникальных ID
    private int nextId = 1;

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

    // Удаление эпиков
    public void deleteEpics() {
        // Удаляем все подзадачи
        subtasks.clear();

        // Очищаем коллекцию эпиков
        epics.clear();

        // Выводим сообщение о завершении операции
        System.out.println("Все эпики и их подзадачи были удалены.");
    }

    public void deleteSubtasks() {
        // Сначала проходим по всем эпикам
        for (Epic epic : epics.values()) {
            // Очистить внутреннее хранилище подзадач у каждого эпика
            epic.clearSubtasks();
            // Пересчитываем статус эпика
            epic.updateStatus(); // Предполагается, что в Epic есть метод для пересчета статуса
        }

        // Удаляем все подзадачи
        subtasks.clear();
    }

    public void deleteAllTasks() { tasks.clear(); subtasks.clear(); epics.clear(); }

    // Получение задачи по идентификатору
    public Task getTaskById(int id) { return tasks.get(id); }

    public Epic getEpicById(int id) { return epics.get(id); }

    public Subtask getSubtaskById(int id) { return subtasks.get(id); }

    // Метод для создания новой задачи
    public void createTask(Task task) {
        // Проверяем, если ID не установлен (например, равен 0 или -1)
        if (task.getId() <= 0) {
            task.setId(nextId); // Присваиваем уникальный ID из счетчика
            nextId++; // Увеличиваем счетчик
        }
        tasks.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        // Проверяем, если ID не установлен (например, равен 0 или -1)
        if (epic.getId() <= 0) {
            epic.setId(nextId); // Присваиваем уникальный ID из счетчика
            nextId++; // Увеличиваем счетчик
        }
        epics.put(epic.getId(), epic); }


    public void createSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getParentTaskID());

        // Проверяем, существует ли эпик
        if (epic != null) {
            // Присваиваем уникальный ID из счетчика, если ID не установлен
            if (subtask.getId() <= 0) {
                subtask.setId(nextId); // Присваиваем уникальный ID
                nextId++; // Увеличиваем счетчик
            }

            // Добавляем подзадачу к эпике и сохраняем в коллекцию подзадач
            epic.addSubtask(subtask);
            subtasks.put(subtask.getId(), subtask);
        } else {
            System.out.println("Не удалось создать подзадачу, так как эпик с ID " + subtask.getParentTaskID() + " не существует.");
        }
    }

    // Обновление задачи
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            // Если задача существует, обновляем её
            tasks.put(task.getId(), task);
            System.out.println("Задача успешно обновлена");
        } else {
            // Если задачи с данным ID нет, выбрасываем исключение или обрабатываем ошибку
            System.out.println("Задача с ID " + task.getId() + " не существует.");
        }
    }

    public void updateEpic(Epic epic) {
        // Проверяем, существует ли эпик в хранилище
        if (epics.containsKey(epic.getId())) {
            // Получаем существующий эпик из хранилища
            Epic existingEpic = epics.get(epic.getId());

            // Обновляем только имя и описание
            existingEpic.setTitle(epic.getTitle());
            existingEpic.setDescription(epic.getDescription());
            // Статус и список подзадач не обновляются, так как они зависят от подзадач
        } else {
            // Эпик с данным ID не найден, ничего не делаем
            System.out.println("Эпик с ID " + epic.getId() + " не существует.");
        }
    }

    public void updateSubtask(Subtask subtask) {
        // 1) Проверяем, есть ли такая подзадача
        if (subtasks.containsKey(subtask.getId())) {
            // Получаем уже существующую подзадачу из хранилища
            Subtask existingSubtask = subtasks.get(subtask.getId());

            // 2) Проверяем, совпадает ли epicId
            if (existingSubtask.getParentTaskID() == (subtask.getParentTaskID())) {
                // 3) Обновляем подзадачу в хранилище
                subtasks.put(subtask.getId(), subtask);

                // 4) Пересчитываем статус Эпика
                Epic epic = epics.get(subtask.getParentTaskID());
                if (epic != null) {
                    epic.updateStatus(); // Предполагается, что у Epic есть метод для обновления статуса
                }
            } else {
                System.out.println("ID не одинаковый. Невозможно обновить подзадачу.");
            }
        } else {
            System.out.println("Подзадача с ID " + subtask.getId() + " не существует. Обновление невозможно.");
        }
    }

    // Удаление задачи по идентификатору
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    // Удаление эпики по идентификатору
    public void deleteEpicById(int id) {
        // 1) Проверяем, существует ли эпик
        if (epics.containsKey(id)) {
            // Получаем эпик, чтобы извлечь его подзадачи
            Epic epic = epics.get(id);

            // 2) Получаем список подзадач, связанных с эпиком
            ArrayList<Subtask> subtasks = epic.getSubtasks(); // Предполагаем, что у вас есть метод для получения подзадач

            // 3) Удаляем все связанные подзадачи
            for (Subtask subtask : new ArrayList<>(subtasks)) {
                subtasks.remove(subtask);
            }

            // 4) Удаляем сам эпик
            epics.remove(id);

            System.out.println("Эпик с ID " + id + " и их подзадачи были удалены.");
        } else {
            System.out.println("Эпик с ID " + id + " не существует. Удаление невозможно.");
        }
    }

    // Удаление подзадачи по идентификатору
    public void deleteSubtaskById(int id) {
        // 1) Удаляем подзадачу из общего хранилища
        Subtask subtask = subtasks.remove(id);

        if (subtask != null) {
            // 2) Получаем эпик, к которому относится удаленная подзадача
            Epic epic = epics.get(subtask.getParentTaskID());

            if (epic != null) {
                // 3) Удаляем идентификатор подзадачи из эпика
                epic.removeSubtaskById(id); //

                // 4) Обновление статуса эпика
                epic.updateStatus();
            }

            System.out.println("Подзадача с ID " + id + " была удалены.");
        } else {
            System.out.println("Подзадача с ID " + id + " не существует. Удаление невоможно.");
        }
    }

    // Получение списка всех подзадач определённого эпика
    public ArrayList<Subtask> getSubtasksOfEpic(int Id) {
        // Получаем эпик по заданному идентификатору
        Epic epic = epics.get(Id);

        // Создаем список для хранения подзадач
        ArrayList<Subtask> epicSubtasks = new ArrayList<>();

        // Проверяем, существует ли эпику и, если да, получаем связанные с ним подзадачи
        if (epic != null) {
            // Получаем подзадач, клиент должен уже иметь данные
            ArrayList<Subtask> subtasks = epic.getSubtasks();

            // Проходим по подзадачам и добавляем соответствующие подзадачи в список
            for (Subtask subtask : subtasks) {
                if (subtask != null) {
                    epicSubtasks.add(subtask);
                }
            }
        } else {
            System.out.println("Эпик ID " + Id + " не существует.");
        }

        return epicSubtasks; // Возвращаем список подзадач
    }
}

