public class Task {

    private String title;           // Название задачи
    private String description;     // Описание задачи
    private int id;                 // Уникальный идентификационный номер задачи
    private TaskStatus status;      // Статус задачи

    // Конструктор для инициализации задачи
    public Task(String title, String description, int id) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = TaskStatus.NEW; // По умолчанию задача создаётся в статусе NEW
    }

    // Геттеры и сеттеры
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Сравнение ссылок
        if (!(obj instanceof Task)) return false; // Проверка типа

        Task other = (Task) obj; // Приведение типа
        return this.id == other.id; // Сравнение идентификаторов
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id); // Возврат хэш-кода для идентификатора
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

}
