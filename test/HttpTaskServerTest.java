import adapter.DurationTypeAdapter;
import adapter.LocalDateTimeTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import manager.TaskManager;
import manager.InMemoryTaskManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import savedfiles.TaskType;
import serializer.TaskDeserializer;
import serializer.TaskSerializer;
import server.HttpTaskServer;
import task.Task;
import task.TaskStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HttpTaskServerTest {
    private static HttpTaskServer taskServer;
    private static TaskManager manager;
    private static Gson gson;
    private static HttpClient client;

    @BeforeAll
    public static void setup() throws IOException {
        manager = new InMemoryTaskManager();
        taskServer = new HttpTaskServer(8080);
        gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(Task.class, new TaskSerializer())
                .registerTypeAdapter(Task.class, new TaskDeserializer())
                .create();
        taskServer.start();
        client = HttpClient.newHttpClient();
    }

    @AfterAll
    public static void tearDown() {
        taskServer.stop();
    }

    @Test
    public void testDeleteAllTasks() throws IOException, InterruptedException {
        Task task = new Task(3, TaskType.TASK, "Task to Delete", TaskStatus.NEW, "Delete me.",
                Duration.ofMinutes(30), LocalDateTime.now());
        String taskJson = gson.toJson(task);

        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();

        client.send(postRequest, HttpResponse.BodyHandlers.ofString());

        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Expected status code for deleting tasks");

        String responseBody = response.body();
        assertTrue(responseBody.contains("All tasks deleted"), "Response should confirm deletion of all tasks");
        assertEquals(0, manager.getAllTasks().size(), "All tasks should be deleted");
    }
}