package social.nickrest.http.data.inter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.sun.net.httpserver.HttpExchange;
import social.nickrest.http.data.Type;

import java.io.File;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.Map;

public interface IResponse {
    HttpExchange getExchange();

    IResponse writeHeader(String key, String value);
    IResponse status(int status);

    Map<String, String> getQuery();

    Type getHttpType();

    String getBody();
    String getPath();

    void write(byte[] response);

    default void write(String response) {
        write(response.getBytes());
    }

    default void write(JsonObject response) {
        writeHeader("Content-Type", "application/json");
        write(response.toString().getBytes());
    }

    default void write(File file) {
        try {
            write(Files.readAllBytes(file.toPath()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default JsonObject getBodyJson() {
        JsonReader reader = new JsonReader(new StringReader(getBody()));
        return JsonParser.parseReader(reader).getAsJsonObject();
    }

    default boolean isValidJson() {
        try {
            JsonParser.parseString(getBody());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}