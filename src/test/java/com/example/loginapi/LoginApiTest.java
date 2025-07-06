package com.example.loginapi;

import com.google.gson.Gson;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginApiTest {

    static Gson gson = new Gson();

    @BeforeAll
    static void startServer() throws Exception {
        UserDatabase db = new UserDatabase("users.json");
        LoginHandler handler = new LoginHandler(db);
        handler.initRoutes();

        // Give Spark time to initialize
        Thread.sleep(1000);
    }

    @AfterAll
    static void stopServer() {
        spark.Spark.stop();
    }

    @Test
    void testLoginSuccess() throws Exception {
        String jsonRequest = gson.toJson(new Credentials("alice", "password123"));

        String response = Request.post("http://localhost:4567/login")
                .body(new StringEntity(jsonRequest, ContentType.APPLICATION_JSON))
                .execute()
                .returnContent()
                .asString();

        assertTrue(response.contains("\"status\":\"success\""));
    }

    @Test
    void testLoginFailure() throws Exception {
        String jsonRequest = gson.toJson(new Credentials("alice", "wrongpass"));

        String response = Request.post("http://localhost:4567/login")
                .body(new StringEntity(jsonRequest, ContentType.APPLICATION_JSON))
                .execute()
                .returnContent()
                .asString();

        assertTrue(response.contains("\"status\":\"failure\""));
    }

    static class Credentials {
        String username;
        String password;

        Credentials(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}

