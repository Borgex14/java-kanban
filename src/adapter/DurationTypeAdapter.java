package adapter;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.Duration;

public class DurationTypeAdapter implements JsonSerializer<Duration>, JsonDeserializer<Duration> {

    @Override
    public JsonElement serialize(Duration duration, Type typeOfSrc, com.google.gson.JsonSerializationContext context) {
        return new JsonPrimitive(duration.getSeconds());
    }

    @Override
    public Duration deserialize(JsonElement json, Type typeOfT, com.google.gson.JsonDeserializationContext context) {
        return Duration.ofSeconds(json.getAsLong());
    }
}
