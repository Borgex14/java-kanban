

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        // Создаем новые сущности
        Task task1 = new Task("Переезд", "Жить в другой город", TaskStatus.NEW);
        Task task2 = new Task("Выполнять работу", "Опеределить все задачи работы", TaskStatus.NEW);
        Epic epic1 = new Epic("Большой переезд", "Переезд из одного города");
        Epic epic2 = new Epic("Выполнить большую работу", "Разделить задачи по этапу");
        Subtask subtask1 = new Subtask("Планировать дату и время", "Записать в бланкнот и напоминание события в телефон за месяц", TaskStatus.IN_PROGRESS, 1);
        Subtask subtask2 = new Subtask("Собирать вещи", "Покупать чемоданы, коробки и рюкзаки", TaskStatus.IN_PROGRESS, 2);


        // Вывод статус задач, эпиков и подзадач
        System.out.println("Статус задачи: " + task1.getStatus());
        System.out.println("Статус подзадачи: " + subtask1.getStatus());
        System.out.println("Статус эпика: " + epic1.getStatus());

        // Создать или добавить задачи
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        //Создать или добавить эпиков в МАП
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        // Cоздать или добавить подзадачи в МАП
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        // Вывод статус задач, эпиков и подзадач после создания или добавления объектов
        System.out.println("Статус задачи: " + task1.getStatus());
        System.out.println("Статус подзадачи: " + subtask1.getStatus());
        System.out.println("Статус эпика: " + epic1.getStatus());

        // Выводим все задачи, эпики и подзадачи
        System.out.println("Задачи: " + taskManager.getAllTasks());
        System.out.println("Эпики: " + taskManager.getAllEpics());
        System.out.println("Подзадачи: " + taskManager.getAllSubtasks());

        // Вывод подзадачи по ID
        System.out.println("Подзадачи ID 5: " + taskManager.getSubtaskById(5));
        System.out.println("Подзадачи ID 6: " + taskManager.getSubtaskById(6));


        // Вывод статус задач, эпиков и подзадач после удаления
        System.out.println("Статус задачи: " + task1.getStatus());
        System.out.println("Статус подзадачи: " + subtask1.getStatus());
        System.out.println("Статус эпика: " + epic1.getStatus());

        // Добавление поздачи в эпиках
        epic1.addSubtask(subtask1);
        epic2.addSubtask(subtask2);

        // Получать подзадачи Эпика по ID
        System.out.println("Подзадачи эпика по ID 3: " + taskManager.getSubtasksOfEpic(3));

        // Выводим все задачи, эпики и подзадачи после добавления подзадчи в эпиках
        System.out.println("Задачи: " + taskManager.getAllTasks());
        System.out.println("Эпики: " + taskManager.getAllEpics());
        System.out.println("Подзадачи: " + taskManager.getAllSubtasks());

        // Например, можем изменить статус подзадачи
        subtask1.setStatus(TaskStatus.DONE);
        System.out.println("Статус подзадачи обновлена: " + subtask1.getStatus());
        subtask2.setStatus(TaskStatus.DONE);
        System.out.println("Статус подзадачи обновлена: " + subtask2.getStatus());
        // Проверяем обновленный статус эпика
        epic1.updateStatus();
        epic2.updateStatus();
        System.out.println("Эпики: " + taskManager.getAllEpics());

        // Обновить эпик
        taskManager.updateEpic(epic1);
        epic1.setTitle("Выполнять работу по полам");
        epic1.setDescription("Выполнить с самогой легкой до самой трудной");
        System.out.println("Эпики: " + taskManager.getAllEpics());

        // Удалить эпики по ID
        taskManager.deleteEpicById(4);
        System.out.println("Эпики: " + taskManager.getAllEpics());

        // удалить все эпики
        taskManager.deleteEpics();
        System.out.println("Подзадачи: " + taskManager.getAllSubtasks());

        // Удалить задачи по ID
        taskManager.deleteTaskById(1);
        System.out.println("Задачи: " + taskManager.getAllTasks());



    }

}

