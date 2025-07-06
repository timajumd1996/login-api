package com.example.loginapi;

import static spark.Spark.*;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

public class LoginHandler {
    private static final Gson gson = new Gson();
    private final UserDatabase db;

    public LoginHandler(UserDatabase db) {
        this.db = db;
    }

    public void initRoutes() {
        post("/login", (req, res) -> {
            Map<String, String> credentials = gson.fromJson(req.body(), Map.class);
            String username = credentials.get("username");
            String password = credentials.get("password");

            Map<String, Object> response = new HashMap<>();
            if (db.validate(username, password)) {
                response.put("status", "success");
                response.put("message", "Login successful!");
            } else {
                response.put("status", "failure");
                response.put("message", "Invalid username or password.");
            }

            res.type("application/json");
            return gson.toJson(response);
        });
    }
}

