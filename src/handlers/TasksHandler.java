package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import manager.TaskManager;
import task.Task;

public class TasksHandler extends BaseHttpHandler implements HttpHandler {

    private final Gson gson;

    public TasksHandler(TaskManager taskManager, Gson gson) {
        super(taskManager);
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Received request: " + exchange.getRequestMethod());
        String requestMethod = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String[] pathSegments = path.split("/");
        try {
            switch (requestMethod) {
                case "GET":
                    if (pathSegments.length == 3 && pathSegments[2].equals("tasks")) {
                        String jsonTasks = gson.toJson(taskManager.getAllTasks());
                        sendText(exchange, jsonTasks, 200);
                    } else if (pathSegments.length == 4 && pathSegments[2].equals("tasks")) {
                        int taskId = Integer.parseInt(pathSegments[3]);
                        Task task = taskManager.getTaskById(taskId);
                        if (task != null) {
                            sendText(exchange, gson.toJson(task), 200);
                        } else {
                            sendText(exchange, "{\"error\":\"Task not found\"}", 404);
                        }
                    } else {
                        sendText(exchange, "{\"error\":\"Invalid path\"}", 400);
                    }
                    break;
                case "POST":
                    InputStream requestBodyStream = exchange.getRequestBody();
                    byte[] requestBodyBites = requestBodyStream.readAllBytes();
                    String requestBody = new String(requestBodyBites, StandardCharsets.UTF_8);
                    Task newTask = gson.fromJson(requestBody, Task.class);
                    if (newTask.getId() != 0) {
                        taskManager.updateTask(newTask);
                        sendText(exchange, gson.toJson(newTask), 200);
                    } else {
                        taskManager.createTask(newTask);
                        String responseBody = gson.toJson(newTask);
                        exchange.sendResponseHeaders(201, responseBody.length());
                        try (OutputStream responseBodyStream = exchange.getResponseBody()) {
                            responseBodyStream.write(responseBody.getBytes(StandardCharsets.UTF_8));
                        }
                    }
                    break;
                case "DELETE":
                    if (pathSegments.length == 4 && pathSegments[2].equals("tasks")) {
                        try {
                            int taskId = Integer.parseInt(pathSegments[3]);
                            if (taskManager.deleteTaskById(taskId)) {
                                sendText(exchange, "{\"message\":\"Task deleted\"}", 200);
                            } else {
                                sendText(exchange, "{\"error\":\"Task not found\"}", 404);
                            }
                        } catch (NumberFormatException e) {
                            sendText(exchange, "{\"error\":\"Invalid ID format\"}", 400);
                        }
                    } else if (pathSegments.length == 3 && pathSegments[2].equals("tasks")) {
                        taskManager.deleteAllTasks();
                        sendText(exchange, "{\"message\":\"All tasks deleted\"}", 200);
                    } else {
                        sendText(exchange, "{\"error\":\"Invalid path\"}", 400);
                    }
                    break;
                default:
                    sendText(exchange, "{\"error\":\"Method not supported\"}", 405);
            }
        } catch (NumberFormatException e) {
            sendText(exchange, "{\"error\":\"Invalid ID format\"}", 400);
        } catch (NotFoundException e) {
            sendNotFound(exchange);
        } catch (NotAcceptableException e) {
            sendHasInteractions(exchange);
        } catch (Exception e) {
            e.printStackTrace();
            sendText(exchange, "{\"error\":\"Internal server error\"}", 500);
        }
    }
}