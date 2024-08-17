import java.util.Collection;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("Выберите действие: ");
            System.out.println("1. Добавить задачу");
            System.out.println("2. Добавить эпик");
            System.out.println("3. Добавить подзадачу");
            System.out.println("4. Получить все задачи");
            System.out.println("5. Удалить все задачи");
            System.out.println("6. Получить все эпики");
            System.out.println("7. Удалить все эпики");
            System.out.println("8. Получить все подзадачи");
            System.out.println("9. Удалить все подзадачи");
            System.out.println("10. Получить задачу по ID");
            System.out.println("11. Получить эпик по ID");
            System.out.println("12. Получить подзадачу по ID");
            System.out.println("13. Получить подзадачи эпика по ID");
            System.out.println("14. Обновить задачи");
            System.out.println("15. Обновить эпики");
            System.out.println("16. Обновить подзадачи");
            System.out.println("17. Удалить задачу по ID");
            System.out.println("18. Удалить эпик по ID");
            System.out.println("19. Удалить подзадачу по ID");
            System.out.println("20. Удалить все задачи полностью");
            System.out.println("21. Выход");
            int command = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            switch (command) {
                case 1:
                    System.out.print("Введите название задачи: ");
                    String taskTitle = scanner.nextLine();
                    System.out.print("Введите описание задачи: ");
                    String taskDescription = scanner.nextLine();
                    int taskId = taskManager.getAllTasks().size() + 1; // генерация ID
                    Task task = new Task(taskTitle, taskDescription, taskId);
                    taskManager.createTask(task);
                    System.out.println("Задача добавлена: " + taskTitle);
                    break;

                case 2:
                    System.out.print("Введите название эпика: ");
                    String epicTitle = scanner.nextLine();
                    System.out.print("Введите описание эпика: ");
                    String epicDescription = scanner.nextLine();
                    int epicId = taskManager.getAllEpics().size() + 1; // генерация ID
                    Epic epic = new Epic(epicTitle, epicDescription, epicId);
                    taskManager.createEpic(epic);
                    System.out.println("Эпик добавлен: " + epicTitle);
                    break;

                case 3:
                    System.out.print("Введите название подзадачи: ");
                    String subtaskTitle = scanner.nextLine();
                    System.out.print("Введите описание подзадачи: ");
                    String subtaskDescription = scanner.nextLine();
                    System.out.print("Введите ID родительской задачи: ");
                    int parentTaskID = scanner.nextInt();
                    int subtaskId = taskManager.getAllSubtasks().size() + 1; // генерация ID
                    Subtask subtask = new Subtask(subtaskTitle, subtaskDescription, subtaskId, parentTaskID);
                    taskManager.createSubtask(subtask);
                    System.out.println("Подзадача добавлена: " + subtaskTitle);
                    break;

                case 4:
                    Collection<Task> allTasks = taskManager.getAllTasks();
                    System.out.println("Все задачи:");
                    for (Task t : allTasks) {
                        System.out.println(t);
                    }
                    break;

                case 5:
                    taskManager.deleteTasks();
                    System.out.println("Все задачи удалены.");
                    break;

                case 6:
                    Collection<Epic> allEpics = taskManager.getAllEpics();
                    System.out.println("Все эпики:");
                    for (Epic e : allEpics) {
                        System.out.println(e);
                    }
                    break;

                case 7:
                    taskManager.deleteEpics();
                    System.out.println("Все эпики удалены");
                    break;

                case 8:
                    Collection<Subtask> allSubtasks = taskManager.getAllSubtasks();
                    System.out.println("Все подзадачи:");
                    for (Subtask s : allSubtasks) {
                        System.out.println(s);
                    }
                    break;

                case 9:
                    taskManager.deleteSubtasks();
                    System.out.println("Все подзадачи удалены");
                    break;

                case 10:
                    System.out.print("Введите ID задачи: ");
                    int uniqueID = scanner.nextInt();
                    Task foundTask = taskManager.getTaskById(uniqueID);
                    if (foundTask != null) {
                        System.out.println("Найдена задача: " + foundTask);
                    } else {
                        System.out.println("Задача с ID " + uniqueID + " не найден");
                    }
                    break;

                case 11:
                    System.out.print("Введите ID эпики: ");
                    int uniqueIDEpic = scanner.nextInt();
                    Epic foundEpic = taskManager.getEpicById(uniqueIDEpic);
                    if (foundEpic != null) {
                        System.out.println("Найдена задача: " + foundEpic);
                    } else {
                        System.out.println("Задача с ID " + uniqueIDEpic + " не найден");
                    }
                    break;

                case 12:
                    System.out.print("Введите ID подзадачи: ");
                    int uniqueIDSubtask = scanner.nextInt();
                    Subtask foundSubtask = taskManager.getSubtaskById(uniqueIDSubtask);
                    if (foundSubtask != null) {
                        System.out.println("Найдена задача: " + foundSubtask);
                    } else {
                        System.out.println("Задача с ID " + uniqueIDSubtask + " не найден");
                    }
                    break;

                case 13:
                    System.out.print("Введите ID эпика для получения подзадач: ");
                    int epicIdSubtask = scanner.nextInt();
                    ArrayList<Subtask> subtasksOfEpic = taskManager.getSubtasksOfEpic(epicIdSubtask);
                    if (subtasksOfEpic.isEmpty()) {
                        System.out.println("У эпика с ID " + epicIdSubtask + " нет подзадач.");
                    } else {
                        System.out.println("Подзадачи эпика с ID " + epicIdSubtask + ":");
                        for (Subtask subtaskForEpic : subtasksOfEpic) {
                            System.out.println(subtaskForEpic);
                        }
                    }
                    break;

                case 14:
                    System.out.print("Введите ID задачи для обновления: ");
                    int updateTaskId = scanner.nextInt();
                    scanner.nextLine(); // Очистка буфера

                    System.out.print("Введите новое название задачи: ");
                    String newTaskTitle = scanner.nextLine();
                    System.out.print("Введите новое описание задачи: ");
                    String newTaskDescription = scanner.nextLine();

                    Task updatedTask = new Task(newTaskTitle, newTaskDescription, updateTaskId);
                    taskManager.updateTask(updatedTask);
                    break;

                case 15:
                    System.out.print("Введите ID эпики для обновления: ");
                    int updateEpicId = scanner.nextInt();
                    scanner.nextLine(); // Очистка буфера

                    System.out.print("Введите новое название задачи: ");
                    String newEpicTitle = scanner.nextLine();
                    System.out.print("Введите новое описание задачи: ");
                    String newEpicDescription = scanner.nextLine();

                    Epic updatedEpic = new Epic(newEpicTitle, newEpicDescription, updateEpicId);
                    taskManager.updateEpic(updatedEpic);
                    break;

                case 16:
                    System.out.print("Введите ID подзадачи для обновления: ");
                    int updateSubtaskId = scanner.nextInt();
                    scanner.nextLine(); // Очистка буфера

                    System.out.print("Введите новое название задачи: ");
                    String newSubtaskTitle = scanner.nextLine();
                    System.out.print("Введите новое описание задачи: ");
                    String newSubtaskDescription = scanner.nextLine();
                    System.out.println("Введите новый ID родительской задачи");
                    int updateTaskSubtaskId = scanner.nextInt();
                    Subtask updatedSubtask = new Subtask(newSubtaskTitle, newSubtaskDescription, updateSubtaskId, updateTaskSubtaskId);
                    taskManager.updateSubtask(updatedSubtask);
                    break;

                case 17:
                    System.out.print("Введите ID задачи для удаления: ");
                    int deleteTaskId = scanner.nextInt();
                    taskManager.deleteTaskById(deleteTaskId);
                    break;

                case 18:
                    System.out.print("Введите ID эпики для удаления: ");
                    int deleteEpicId = scanner.nextInt();
                    taskManager.deleteEpicById(deleteEpicId);
                    break;

                case 19:
                    System.out.print("Введите ID подзадачу для удаления: ");
                    int deleteSubtaskId = scanner.nextInt();
                    taskManager.deleteSubtaskById(deleteSubtaskId);
                    break;

                case 20:
                    taskManager.deleteAllTasks();
                    System.out.println("Все задачи полностью удалены.");
                    break;

                case 21:
                    System.out.println("Хорошего дня");
                    scanner.close();
                    return;

                default:
                    System.out.println("Неверный выбор, попробуйте еще раз.");
            }
        }
    }

}