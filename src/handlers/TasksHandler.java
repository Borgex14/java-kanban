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
        try {
            switch (requestMethod) {
                case "GET":
                    String jsonTasks = gson.toJson(taskManager.getAllTasks());
                    sendText(exchange, jsonTasks, 200);
                    break;
                case "POST":
                    InputStream requestBodyStream = exchange.getRequestBody();
                    byte[] requestBodyBites = requestBodyStream.readAllBytes();
                    String requestBody = new String (requestBodyBites, StandardCharsets.UTF_8);
                    Task newTask = gson.fromJson(requestBody, Task.class);
                    System.out.println();
                    taskManager.createTask(newTask);
                    String responseBody = gson.toJson(newTask);
                    exchange.sendResponseHeaders(201, responseBody.length());
                    try (OutputStream responseBodyStream = exchange.getResponseBody()) {
                        responseBodyStream.write(responseBody.getBytes(StandardCharsets.UTF_8));
                    }
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
            e.printStackTrace();
            sendText(exchange, "{\"error\":\"Internal server error\"}", 500);
        }
    }
}