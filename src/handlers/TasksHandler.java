package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import manager.TaskManager;
import task.Task;

public class TasksHandler extends BaseHttpHandler implements HttpHandler {

    public TasksHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            switch (exchange.getRequestMethod()) {
                case "GET":
                    String jsonTasks = gson.toJson(taskManager.getAllTasks());
                    sendText(exchange, jsonTasks, 200);
                    break;
                case "POST":
                    Task newTask = readJson(exchange, Task.class);
                    taskManager.createTask(newTask);
                    sendText(exchange, gson.toJson(newTask), 201);
                    break;
                case "DELETE":
                    taskManager.deleteAllTasks();
                    sendText(exchange, "{\"message\":\"All tasks deleted\"}", 200);
                    break;
                default:
                    sendText(exchange, "{\"error\":\"Method not supported\"}", 405);
            }
        } catch (NotFoundException e) {
            sendNotFound(exchange);
        } catch (NotAcceptableException e) {
            sendHasInteractions(exchange);
        } catch (Exception e) {
            sendText(exchange, "{\"error\":\"Internal server error\"}", 500);
        }
    }
}
