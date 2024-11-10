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
                .create();
        taskServer.start();
        client = HttpClient.newHttpClient();
    }

    @AfterAll
    public static void tearDown() {
        taskServer.stop();
    }

    @Test
    public void testAddNewTasks() throws IOException, InterruptedException {
        Task newTask = new Task( "New Task", TaskStatus.NEW, "This is a new task.");
        String taskJson = gson.toJson(newTask);

        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();

        client.send(postRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(0, manager.getAllTasks().size(), "All tasks should be deleted");
    }
}