package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
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
        try {
            switch (exchange.getRequestMethod()) {
                case "GET":
                    String jsonEpics = gson.toJson(taskManager.getAllEpics());
                    sendText(exchange, jsonEpics, 200);
                    break;
                case "POST":
                    Epic newEpic = readJson(exchange, Epic.class);
                    taskManager.createEpic(newEpic);
                    sendText(exchange, gson.toJson(newEpic), 201);
                    break;
                case "DELETE":
                    taskManager.deleteEpics();
                    sendText(exchange, "{\"message\":\"All Epics deleted\"}", 200);
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
