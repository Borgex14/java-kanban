package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import manager.TaskManager;

public class PrioritizedHandler extends BaseHttpHandler implements HttpHandler {

    public PrioritizedHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equals("GET")) {
                String jsonPrioritizedTasks = gson.toJson(taskManager.getPrioritizedTasks());
                sendText(exchange, jsonPrioritizedTasks, 200);
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