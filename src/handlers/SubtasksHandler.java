package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import manager.TaskManager;
import task.Subtask;

public class SubtasksHandler extends BaseHttpHandler implements HttpHandler {

    public SubtasksHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            switch (exchange.getRequestMethod()) {
                case "GET":
                    String jsonSubtasks = gson.toJson(taskManager.getAllSubtasks());
                    sendText(exchange, jsonSubtasks, 200);
                    break;
                case "POST":
                    Subtask newSubtask = readJson(exchange, Subtask.class);
                    taskManager.createSubtask(newSubtask);
                    sendText(exchange, gson.toJson(newSubtask), 201);
                    break;
                case "DELETE":
                    // Реализация удаления задачи
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
