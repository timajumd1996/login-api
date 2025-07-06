package com.example.loginapi;

public class Main {
    public static void main(String[] args) {
        UserDatabase db = new UserDatabase("users.json");
        LoginHandler handler = new LoginHandler(db);
        handler.initRoutes();
        System.out.println("Server running at: http://localhost:4567/login");
    }
}

