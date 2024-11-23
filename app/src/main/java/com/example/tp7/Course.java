package com.example.tp7;

public class Course {
    private int id;
    private String name;
    private float hours;
    private String type;
    private int teacherId;


    public Course() {
    }


    public Course(int id, String name, float hours, String type, int teacherId) {
        this.id = id;
        this.name = name;
        this.hours = hours;
        this.type = type;
        this.teacherId = teacherId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}

