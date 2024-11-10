package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.URI;
import manager.TaskManager;
import task.Epic;

public class EpicsHandler extends BaseHttpHandler implements HttpHandler {

    private final Gson gson;

    public EpicsHandler(TaskManager taskManager, Gson gson) {
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
                    if (pathSegments.length == 2 && pathSegments[1].equals("epics")) {
                        String jsonEpics = gson.toJson(taskManager.getAllEpics());
                        sendText(exchange, jsonEpics, 200);
                    } else if (pathSegments.length == 3 && pathSegments[1].equals("epics")) {
                        int epicId = Integer.parseInt(pathSegments[2]);
                        Epic epic = taskManager.getEpicById(epicId);
                        if (epic != null) {
                            sendText(exchange, gson.toJson(epic), 200);
                        } else {
                            sendText(exchange, "{\"error\":\"Epic not found\"}", 404);
                        }
                    } else {
                        sendText(exchange, "{\"error\":\"Invalid path\"}", 400);
                    }
                    break;
                case "POST":
                    Epic newEpic = readJson(exchange, Epic.class);
                    if (newEpic.getId() != null && newEpic.getId() != 0) {
                        taskManager.updateEpic(newEpic);
                        sendText(exchange, gson.toJson(newEpic), 200);
                    } else {
                        taskManager.createEpic(newEpic);
                        sendText(exchange, gson.toJson(newEpic), 201);
                    }
                    break;
                case "DELETE":
                    if (pathSegments.length == 3 && pathSegments[1].equals("epics")) {
                        try {
                            int epicId = Integer.parseInt(pathSegments[2]);
                            if (taskManager.deleteEpicById(epicId)) {
                                sendText(exchange, "{\"message\":\"Epic deleted\"}", 200);
                            } else {
                                sendText(exchange, "{\"error\":\"Epic not found\"}", 404);
                            }
                        } catch (NumberFormatException e) {
                            sendText(exchange, "{\"error\":\"Invalid ID format\"}", 400);
                        }
                    } else if (pathSegments.length == 2 && pathSegments[1].equals("epics")) {
                        taskManager.deleteEpics();
                        sendText(exchange, "{\"message\":\"All epics deleted\"}", 200);
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
