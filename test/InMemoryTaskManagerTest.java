package tests;

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
    void BeforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    void addNewTask__ShouldSaveTAsk() {
        Task task = new Task("Выполнить Задачу", "Выполнить по этапам", TaskStatus.NEW);
        Task expeectedTask = new Task("Выполнить Задачу", "Выполнить по этапам", TaskStatus.NEW, 1);

        Task actual = taskManager.createTask(task);

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(expeectedTask, actual);
    }

    @Test
    void updateTask__shouldUpdateID() {
        Task task = new Task("Выполнить работу", "Выполнить по этапам работы", TaskStatus.NEW);
        Task AddedTask = taskManager.createTask(task);
        Task UpdatedTask = new Task("Выполнить работу завтра", "Выполнить по этапам работы", TaskStatus.NEW, AddedTask.getId());

        Task expectedTask = new Task("Выполнить работу завтра", "Выполнить по этапам работы", TaskStatus.NEW, AddedTask.getId());

        Task actualupdateTask = taskManager.updateTask(UpdatedTask);

        Assertions.assertEquals(expectedTask, actualupdateTask);
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



