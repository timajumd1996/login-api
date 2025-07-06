package com.example.loginapi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class UserDatabase {
    private List<User> users;

    public UserDatabase(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            Type userListType = new TypeToken<List<User>>() {}.getType();
            users = gson.fromJson(reader, userListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean validate(String username, String password) {
        return users.stream()
                .anyMatch(u -> u.getUsername().equals(username)
                        && u.getPassword().equals(password));
    }
}

