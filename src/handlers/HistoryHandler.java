package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import manager.TaskManager;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {

    private final Gson gson;

    public HistoryHandler(TaskManager taskManager, Gson gson) {
        super(taskManager);
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equals("GET")) {
                String jsonHistory = gson.toJson(taskManager.getHistory());
                sendText(exchange, jsonHistory, 200);
            } else {
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

