import java.util.List;
import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskState;


public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Переезд",  TaskState.NEW, "Жить в другой город");
        Task task2 = new Task("Выполнять работу", TaskState.NEW, "Опеределить все задачи работы");
        Epic epic1 = new Epic("Большой переезд", "Переезд из одного города");
        epic1.setId(1);
        Epic epic2 = new Epic("Выполнить большую работу", "Разделить задачи по этапу");
        epic2.setId(2);
        Subtask subtask1 = new Subtask("Планировать дату и время", "Записать в бланкнот и напоминание события в телефон за месяц", TaskState.IN_PROGRESS, 1);
        Subtask subtask2 = new Subtask("Собирать вещи", "Покупать чемоданы, коробки и рюкзаки", TaskState.IN_PROGRESS, 2);

        System.out.println("Статус задачи: " + task1.getState());
        System.out.println("Статус эпика: " + epic1.getState());
        System.out.println("Статус подзадачи: " + subtask1.getState());

        taskManager.createTask(task1);
        taskManager.createTask(task2);

        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);

        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        System.out.println("Статус задачи: " + task1.getState());
        System.out.println("Статус подзадачи: " + subtask1.getState());
        System.out.println("Статус эпика: " + epic1.getState());

        System.out.println("Задачи: " + taskManager.getAllTasks());
        System.out.println("Эпики: " + taskManager.getAllEpics());
        System.out.println("Подзадачи: " + taskManager.getAllSubtasks());

        taskManager.getTaskById(2);
        taskManager.getEpicById(1);
        taskManager.getEpicById(1);
        taskManager.getSubtaskById(4);
        taskManager.getSubtaskById(4);
        taskManager.getTaskById(2);
        List<Task> history = taskManager.getHistory();

        System.out.println("История просмотренных задач, эпик и подзадач:");
        for (Task task : history) {
            System.out.println(task);
        }
        taskManager.deleteTaskById(2);
        taskManager.deleteEpicById(1);
        taskManager.deleteSubtaskById(4);



        taskManager.getEpicById(1);
        taskManager.getSubtaskById(4);
        taskManager.getTaskById(2);
        List<Task> history1 = taskManager.getHistory();

        System.out.println("История просмотренных задач, эпик и подзадач:");
        for (Task task : history1) {
            System.out.println(task);
        }

        System.out.println("Подзадачи ID 3: " + taskManager.getSubtaskById(3));
        System.out.println("Подзадачи ID 4: " + taskManager.getSubtaskById(4));

        System.out.println("Статус задачи: " + task1.getState());
        System.out.println("Статус подзадачи: " + subtask1.getState());
        System.out.println("Статус эпика: " + epic1.getState());

        epic1.addSubtask(subtask1);
        epic2.addSubtask(subtask2);

        System.out.println("Подзадачи эпика по ID 2: " + taskManager.getSubtasksOfEpic(2));

        System.out.println("Задачи: " + taskManager.getAllTasks());
        System.out.println("Эпики: " + taskManager.getAllEpics());
        System.out.println("Подзадачи: " + taskManager.getAllSubtasks());

        subtask1.setState(TaskState.DONE);
        System.out.println("Статус подзадачи обновлена: " + subtask1.getState());
        subtask2.setState(TaskState.DONE);
        System.out.println("Статус подзадачи обновлена: " + subtask2.getState());

        epic1.updateEpicState();
        epic2.updateEpicState();
        System.out.println("Эпики: " + taskManager.getAllEpics());

        taskManager.updateSubtask(subtask1);
        subtask1.setId(3);
        subtask1.setName("Планировать новую дату и новое время");
        subtask1.setDescription("Перезаписать всё");
        subtask1.setState(TaskState.IN_PROGRESS);
        subtask1.setParentTaskID(1);
        epic1.updateEpicState();

        taskManager.updateEpic(epic1);
        epic1.setName("Выполнять работу по полам");
        epic1.setDescription("Выполнить с самогой легкой до самой трудной");
        System.out.println("Эпики: " + taskManager.getEpicById(1));

        taskManager.deleteEpicById(1);
        System.out.println("Эпики: " + taskManager.getAllEpics());

        taskManager.deleteEpics();
        System.out.println("Подзадачи: " + taskManager.getAllSubtasks());

        taskManager.deleteTaskById(1);
        System.out.println("Задачи: " + taskManager.getAllTasks());
    }
}

