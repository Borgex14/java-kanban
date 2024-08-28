import java.util.List;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Переезд", "Жить в другой город", TaskStatus.NEW);
        Task task2 = new Task("Выполнять работу", "Опеределить все задачи работы", TaskStatus.NEW);
        Epic epic1 = new Epic("Большой переезд", "Переезд из одного города");
        epic1.setId(1);
        Epic epic2 = new Epic("Выполнить большую работу", "Разделить задачи по этапу");
        epic2.setId(2);
        Subtask subtask1 = new Subtask("Планировать дату и время", "Записать в бланкнот и напоминание события в телефон за месяц", TaskStatus.IN_PROGRESS, 1);
        Subtask subtask2 = new Subtask("Собирать вещи", "Покупать чемоданы, коробки и рюкзаки", TaskStatus.IN_PROGRESS, 2);

        System.out.println("Статус задачи: " + task1.getStatus());
        System.out.println("Статус эпика: " + epic1.getStatus());
        System.out.println("Статус подзадачи: " + subtask1.getStatus());

        taskManager.createTask(task1);
        taskManager.createTask(task2);

        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);

        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        System.out.println("Статус задачи: " + task1.getStatus());
        System.out.println("Статус подзадачи: " + subtask1.getStatus());
        System.out.println("Статус эпика: " + epic1.getStatus());

        System.out.println("Задачи: " + taskManager.getAllTasks());
        System.out.println("Эпики: " + taskManager.getAllEpics());
        System.out.println("Подзадачи: " + taskManager.getAllSubtasks());

        taskManager.getTaskById(2);
        taskManager.getEpicById(1);
        taskManager.getSubtaskById(4);
        List<Task> history = taskManager.getHistory();

        System.out.println("История просмотренных задач, эпик и подзадач:");
        for (Task task : history) {
            System.out.println(task);
        }

        System.out.println("Подзадачи ID 3: " + taskManager.getSubtaskById(3));
        System.out.println("Подзадачи ID 4: " + taskManager.getSubtaskById(4));

        System.out.println("Статус задачи: " + task1.getStatus());
        System.out.println("Статус подзадачи: " + subtask1.getStatus());
        System.out.println("Статус эпика: " + epic1.getStatus());

        epic1.addSubtask(subtask1);
        epic2.addSubtask(subtask2);

        System.out.println("Подзадачи эпика по ID 2: " + taskManager.getSubtasksOfEpic(2));

        System.out.println("Задачи: " + taskManager.getAllTasks());
        System.out.println("Эпики: " + taskManager.getAllEpics());
        System.out.println("Подзадачи: " + taskManager.getAllSubtasks());

        subtask1.setStatus(TaskStatus.DONE);
        System.out.println("Статус подзадачи обновлена: " + subtask1.getStatus());
        subtask2.setStatus(TaskStatus.DONE);
        System.out.println("Статус подзадачи обновлена: " + subtask2.getStatus());

        epic1.updateStatus();
        epic2.updateStatus();
        System.out.println("Эпики: " + taskManager.getAllEpics());

        taskManager.updateEpic(epic1);
        epic1.setTitle("Выполнять работу по полам");
        epic1.setDescription("Выполнить с самогой легкой до самой трудной");
        System.out.println("Эпики: " + taskManager.getAllEpics());

        taskManager.deleteEpicById(1);
        System.out.println("Эпики: " + taskManager.getAllEpics());

        taskManager.deleteEpics();
        System.out.println("Подзадачи: " + taskManager.getAllSubtasks());

        taskManager.deleteTaskById(1);
        System.out.println("Задачи: " + taskManager.getAllTasks());
    }
}

