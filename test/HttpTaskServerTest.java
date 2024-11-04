import adapter.DurationTypeAdapter;
import adapter.LocalDateTimeTypeAdapter;
import com.google.gson.Gson;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import server.HttpTaskServer;
import task.Task;
import task.TaskStatus;
import com.google.gson.GsonBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static savedfiles.TaskType.TASK;

public class HttpTaskServerTest {

    private HttpTaskServer taskServer;
    private TaskManager manager;
    private Gson gson;
    private HttpClient client;

    @BeforeEach
    public void setUp() throws IOException {
        manager = new InMemoryTaskManager();
        taskServer = new HttpTaskServer(8080);
        gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
        client = HttpClient.newHttpClient();
        taskServer.start();
    }

    @AfterEach
    public void tearDown() {
        taskServer.stop();
    }

    @Test
    public void testServerStart() {
        assertDoesNotThrow(() -> taskServer.start(), "Server should start without throwing exceptions");
    }

    @Test
    public void testAddTask() throws IOException, InterruptedException {
        Task task = new Task(1, TASK,"Test Task", TaskStatus.NEW,"Test Description",
                Duration.ofMinutes(5), LocalDateTime.now());

        String taskJson = gson.toJson(task);

        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode(), "Expected status code for created task");

        assertEquals(1, manager.getAllTasks().size(), "The number of tasks should be 1");
        assertEquals("Test Task", manager.getAllTasks().getClass().getName(), "Task name should match");
    }

    @Test
    public void testGetTasks() throws IOException, InterruptedException {
        Task task = new Task(1, TASK,"Test Task", TaskStatus.NEW,"Test Description", Duration.ofMinutes(5), LocalDateTime.now());
        String taskJson = gson.toJson(task);
        URI url = URI.create("http://localhost:8080/tasks");

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();

        client.send(postRequest, HttpResponse.BodyHandlers.ofString());

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "Expected status code for retrieving tasks");
        assertTrue(response.body().contains("Test Task"), "Response should contain the task's name");
    }
}