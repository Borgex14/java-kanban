import java.util.ArrayList;


public class Epic extends Task {
    private final ArrayList<Subtask> subtasks;  // Приватная переменная подзадач // Для подзадач
    private ArrayList<Integer> subtaskIds; // Список идентификаторов подзадач
    private TaskStatus status;

    // // Конструктор названием и описанием, который по умолчанию устанавливает статус NEW
    public Epic(String title, String description) {
        super(title, description, TaskStatus.NEW);
        this.subtasks = new ArrayList<>();
        this.subtaskIds = new ArrayList<>(); // Инициализируем список подзадач
    }

    // Конструктор с ID, названием и описанием
    public Epic(String title, String description, int id) {
        super(title, description, TaskStatus.NEW, id); // По умолчанию задача создаётся в статусе NEW
        this.subtasks = new ArrayList<>();
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks); // Возвращаем копию списка, чтобы защитить оригинальный
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        // Устанавливаем родителя для подзадачи
        subtask.setParentTaskID(this.getId());
        // Пересчитываем статус после добавления подзадачи
        updateStatus();
    }

    protected void updateStatus() {
        if (subtasks.isEmpty()) {
            status = TaskStatus.NEW; // Если нет подзадач, статус "Новый"
        } else {
            boolean allDone = true;
            boolean anyInProgress = false;

            for (Subtask subtask : subtasks) {
                if (subtask.getStatus() != TaskStatus.DONE) {
                    allDone = false;
                }
                if (subtask.getStatus() == TaskStatus.IN_PROGRESS) {
                    anyInProgress = true;
                }
            }

            if (allDone) {
                status = TaskStatus.DONE;
            } else if (anyInProgress) {
                status = TaskStatus.IN_PROGRESS;
            } else {
                status = TaskStatus.NEW; // Если нет подзадач в процессе и не все завершены
            }
        }
    }

    // Метод для получения ID подзадач
    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    // Метод добавления
    public void addSubtaskIds(int id) {
        subtaskIds.add(id);
    }


    // Метод для удаления единичной подзадачи по ID
    public void removeSubtaskById(int id) {
        subtasks.remove(id);
    }

    public void removeSubtaskId(int subtaskId) {
        subtaskIds.remove(Integer.valueOf(subtaskId)); // Удаляем идентификатор подзадачи
    }

    // Метод для очистки всех данных в хранилище подзадач
    public void clearSubtasks() {
        subtasks.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + status +
                ", subtasks=" + getSubtasks() +
                '}';
    }

}

