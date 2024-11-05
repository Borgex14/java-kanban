package server;

import adapter.DurationTypeAdapter;
import adapter.LocalDateTimeTypeAdapter;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import handlers.EpicsHandler;
import handlers.HistoryHandler;
import handlers.PrioritizedHandler;
import handlers.SubtasksHandler;
import handlers.TasksHandler;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;
import manager.Managers;
import manager.TaskManager;
import com.google.gson.GsonBuilder;
import serializer.TaskDeserializer;
import serializer.TaskSerializer;
import task.Task;

public class HttpTaskServer {
    private final int port;
    private final Gson gson;
    private HttpServer httpServer;
    private final TaskManager taskManager;

    public HttpTaskServer(int port) {
        this.port = port;
        taskManager = Managers.getDefault();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(Task.class, new TaskSerializer())
                .registerTypeAdapter(Task.class, new TaskDeserializer())
                .create();
    }

    public void start() throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        httpServer.createContext("/tasks", new TasksHandler(taskManager, gson));
        httpServer.createContext("/subtasks", new SubtasksHandler(taskManager, gson));
        httpServer.createContext("/epics", new EpicsHandler(taskManager, gson));
        httpServer.createContext("/history", new HistoryHandler(taskManager, gson));
        httpServer.createContext("/prioritized", new PrioritizedHandler(taskManager, gson));
        httpServer.start();
        System.out.println("Сервер запущен на порту " + httpServer.getAddress().getPort());
    }

    public void stop() {
        httpServer.stop(0);
        System.out.println("Server stopped.");
    }

    public static void main(String[] args) throws IOException {
            HttpTaskServer server = new HttpTaskServer(8080);
            server.start();
    }
}