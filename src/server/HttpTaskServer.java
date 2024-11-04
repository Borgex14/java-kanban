package server;

import com.sun.net.httpserver.HttpServer;

import handlers.EpicsHandler;
import handlers.HistoryHandler;
import handlers.PrioritizedHandler;
import handlers.SubtasksHandler;
import handlers.TasksHandler;
import java.io.IOException;
import java.net.InetSocketAddress;
import manager.Managers;
import manager.TaskManager;

public class HttpTaskServer {
    private final HttpServer server;
    private final TaskManager taskManager;

    public HttpTaskServer(int port) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        this.taskManager = Managers.getDefault(); // Получаем экземпляр менеджера задач
        server.createContext("/tasks", new TasksHandler(taskManager));
        server.createContext("/subtasks", new SubtasksHandler(taskManager));
        server.createContext("/epics", new EpicsHandler(taskManager));
        server.createContext("/history", new HistoryHandler(taskManager));
        server.createContext("/prioritized", new PrioritizedHandler(taskManager));
    }

    public void start() {
        server.start();
        System.out.println("Сервер запущен на порту " + server.getAddress().getPort());
    }

    public void stop() {
        server.stop(0);
        System.out.println("Server stopped.");
    }

    public static void main(String[] args) {
        try {
            HttpTaskServer server = new HttpTaskServer(8080);
            server.start();
            server.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

