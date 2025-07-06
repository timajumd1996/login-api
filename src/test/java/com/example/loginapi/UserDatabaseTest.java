package com.example.loginapi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDatabaseTest {

    static UserDatabase db;

    @BeforeAll
    static void setup() {
        db = new UserDatabase("users.json");
    }

    @Test
    void testValidUser() {
        assertTrue(db.validate("alice", "password123"));
    }

    @Test
    void testInvalidPassword() {
        assertFalse(db.validate("alice", "wrongpass"));
    }

    @Test
    void testUnknownUser() {
        assertFalse(db.validate("notfound", "whatever"));
    }
}

