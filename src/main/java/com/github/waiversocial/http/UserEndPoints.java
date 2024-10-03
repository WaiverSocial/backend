package com.github.waiversocial.http;

import com.github.waiversocial.Main;
import com.google.gson.JsonObject;
import lombok.NonNull;
import social.nickrest.http.advanced.AdvancedRequest;
import social.nickrest.http.data.annotation.advanced.BaseEndPoint;
import social.nickrest.http.data.annotation.advanced.Path;
import social.nickrest.http.data.inter.IResponse;

import static social.nickrest.http.data.Type.*;

@BaseEndPoint("/api")
public class UserEndPoints extends AdvancedRequest {

    @Path(value = "/ping", type = GET)
    public void onPing(@NonNull IResponse response) {
        response.status(200)
                .writeHeader("Content-Type", "text/html")
                .write("<pre>Pong!</pre>");
    }

    @Path(value = "/kill", type = GET)
    public void onKill(@NonNull IResponse response) {
        response.status(200)
                .writeHeader("Content-Type", "text/html")
                .write("<pre>Shutting down...</pre>");
        Main.getDatabase().close();

    }

    @Path(value = "/user/register", type = POST)
    public void onRegister(@NonNull IResponse response) {
        if(!response.isValidJson()) {
            JsonObject error = new JsonObject();    
            error.addProperty("error", "Invalid JSON");
            response.status(400)
                    .writeHeader("Content-Type", "application/json")
                    .write(error);
            return;
        }

        JsonObject object = response.getBodyJson();
        if(!object.has("username") || !object.has("password")) {
            JsonObject error = new JsonObject();
            error.addProperty("error", "Missing username or password");

            response.status(400)
                    .writeHeader("Content-Type", "application/json")
                    .write(error);

            return;
        }

        String username = object.get("username").getAsString();
        String password = object.get("password").getAsString();

        JsonObject success = new JsonObject();
        success.addProperty("success", "User registered");

        JsonObject user = new JsonObject();
        user.addProperty("username", username);
        success.add("user", user);

        response.status(200)
                .writeHeader("Content-Type", "application/json")
                .write(success);
    }

}
