package com.example.tp7;

public class Teacher {
    private int id;
    private String name;
    private String email;

    public Teacher(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

