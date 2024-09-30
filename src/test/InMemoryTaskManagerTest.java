package test;

import manager.TaskManager;
import manager.Managers;
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
        Task task = new Task("Выполнить Задачу", "Выполнить по этапам", TaskStatus.NEW);
        Task expectedTask = new Task("Выполнить Задачу", "Выполнить по этапам", TaskStatus.NEW, 1); // Исправлено на expectedTask

        Task actual = taskManager.createTask(task);

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(expectedTask, actual);
    }

    @Test
    void updateTask__shouldUpdateID() {
        Task task = new Task("Выполнить работу", "Выполнить по этапам работы", TaskStatus.NEW);
        Task addedTask = taskManager.createTask(task); // Изменено имя переменной
        Task updatedTask = new Task("Выполнить работу завтра", "Выполнить по этапам работы", TaskStatus.NEW, addedTask.getId()); // Изменено имя переменной

        Task expectedTask = new Task("Выполнить работу завтра", "Выполнить по этапам работы", TaskStatus.NEW, addedTask.getId());

        Task actualUpdateTask = taskManager.updateTask(updatedTask); // Исправлено название переменной

        Assertions.assertEquals(expectedTask, actualUpdateTask);
    }

    @Test
    void withoutFieldsChangesOfTask() {
        Task task = new Task("Выполнить Задачу", "Выполнить по этапам", TaskStatus.NEW);
        Task expectedTask = new Task("Выполнить Задачу", "Выполнить по этапам", TaskStatus.NEW, 1);

        Task actualTask = taskManager.createTask(task);

        Assertions.assertEquals(expectedTask.getTitle(), actualTask.getTitle());
        Assertions.assertEquals(expectedTask.getDescription(), actualTask.getDescription());
        Assertions.assertEquals(expectedTask.getStatus(), actualTask.getStatus());
    }
}



