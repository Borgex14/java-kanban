

import manager.TaskManager;
import manager.Managers;
import savedfiles.TaskType;
import task.TaskStatus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Task;

class InMemoryTaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {  // Имя метода изменено на корректное
        taskManager = Managers.getDefault();
    }

    @Test
    void addNewTask__ShouldSaveTask() {
        Task task = new Task("Выполнить Задачу",TaskStatus.NEW, "Выполнить по этапам");
        Task actual = taskManager.createTask(task);
        Task expectedTask = new Task("Выполнить Задачу", "Выполнить по этапам", actual.getId(),TaskStatus.NEW);

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(expectedTask, actual);
    }

    @Test
    void updateTask__shouldUpdateID() {
        Task task = new Task("Выполнить работу",TaskStatus.NEW, "Выполнить по этапам работы");
        Task addedTask = taskManager.createTask(task);
        Task updatedTask = new Task("Выполнить работу завтра","Выполнить по этапам работы", addedTask.getId(), TaskStatus.NEW);

        Task expectedTask = new Task("Выполнить работу завтра","Выполнить по этапам работы", addedTask.getId(), TaskStatus.NEW);

        Task actualUpdateTask = taskManager.updateTask(updatedTask);

        Assertions.assertEquals(expectedTask, actualUpdateTask);
    }

    @Test
    void withoutFieldsChangesOfTask() {
        Task task = new Task("Выполнить Задачу",TaskStatus.NEW, "Выполнить по этапам");
        Task expectedTask = new Task(1, TaskType.TASK, "Выполнить Задачу", TaskStatus.NEW, "Выполнить по этапам");

        Task actualTask = taskManager.createTask(task);

        Assertions.assertEquals(expectedTask.getName(), actualTask.getName());
        Assertions.assertEquals(expectedTask.getDescription(), actualTask.getDescription());
        Assertions.assertEquals(expectedTask.getStatus(), actualTask.getStatus());
    }
}



