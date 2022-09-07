package com.nixal.ssobchenko.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class GsonUtil {
    private final Gson gson;

    public GsonUtil() {
        JsonSerializer<LocalDateTime> ser = (localDateTime, type, jsonSerializationContext) ->
                localDateTime == null ? null : new JsonPrimitive(localDateTime.toString());
        JsonDeserializer<LocalDateTime> deser = (json, typeOfT, context) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
            return LocalDateTime.parse(json.getAsString(), formatter);
        };
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, ser)
                .registerTypeAdapter(LocalDateTime.class, deser).create();
    }
}