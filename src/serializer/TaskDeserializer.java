package serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import savedfiles.TaskType;
import task.Task;
import task.TaskStatus; // Убедитесь, что TaskStatus доступен

public class TaskDeserializer implements JsonDeserializer<Task> {
    @Override
    public Task deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        int id = jsonObject.get("id").getAsInt();
        TaskType type = TaskType.valueOf(jsonObject.get("type").getAsString());
        String name = jsonObject.get("name").getAsString();
        TaskStatus status = TaskStatus.valueOf(jsonObject.get("status").getAsString()); // Убедитесь, что есть соответствие
        String description = jsonObject.get("description").getAsString();
        Duration duration = Duration.parse(jsonObject.get("duration").getAsString()); // Предполагается ISO-формат
        LocalDateTime startTime = LocalDateTime.parse(jsonObject.get("startTime").getAsString());

        return new Task(id, type, name, status, description, duration, startTime);
    }
}

