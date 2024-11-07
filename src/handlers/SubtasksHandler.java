package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.URI;
import manager.TaskManager;
import task.Subtask;

public class SubtasksHandler extends BaseHttpHandler implements HttpHandler {

    private final Gson gson;

    public SubtasksHandler(TaskManager taskManager, Gson gson) {
        super(taskManager);
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String path = uri.getPath();
        String[] pathSegments = path.split("/");
        try {
            switch (exchange.getRequestMethod()) {
                case "GET":
                    if (pathSegments.length == 3 && pathSegments[2].equals("subtasks")) {
                        String jsonSubtasks = gson.toJson(taskManager.getAllSubtasks());
                        sendText(exchange, jsonSubtasks, 200);
                    } else if (pathSegments.length == 4 && pathSegments[2].equals("subtasks")) {
                        int subtaskId = Integer.parseInt(pathSegments[3]);
                        Subtask subtask = taskManager.getSubtaskById(subtaskId);
                        if (subtask != null) {
                            sendText(exchange, gson.toJson(subtask), 200);
                        } else {
                            sendText(exchange, "{\"error\":\"Subtask not found\"}", 404);
                        }
                    } else {
                        sendText(exchange, "{\"error\":\"Invalid path\"}", 400);
                    }
                    break;
                case "POST":
                    Subtask newSubtask = readJson(exchange, Subtask.class);
                    if (newSubtask.getId() != null && newSubtask.getId() != 0) {
                        taskManager.updateSubtask(newSubtask);
                        sendText(exchange, gson.toJson(newSubtask), 200);
                    } else {
                        taskManager.createSubtask(newSubtask);
                        sendText(exchange, gson.toJson(newSubtask), 201);
                    }
                    break;
                case "DELETE":
                    if (pathSegments.length == 4 && pathSegments[2].equals("subtasks")) {
                        try {
                            int subtaskId = Integer.parseInt(pathSegments[3]);
                            if (taskManager.deleteSubtaskById(subtaskId)) {
                                sendText(exchange, "{\"message\":\"Subtask deleted\"}", 200);
                            } else {
                                sendText(exchange, "{\"error\":\"Subtask not found\"}", 404);
                            }
                        } catch (NumberFormatException e) {
                            sendText(exchange, "{\"error\":\"Invalid ID format\"}", 400);
                        }
                    } else if (pathSegments.length == 3 && pathSegments[2].equals("subtasks")) {
                        taskManager.deleteSubtasks();
                        sendText(exchange, "{\"message\":\"All subtasks deleted\"}", 200);
                    } else {
                        sendText(exchange, "{\"error\":\"Invalid path\"}", 400);
                    }
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
