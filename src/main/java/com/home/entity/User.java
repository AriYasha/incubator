package com.home.entity;

public class User {
    private int userId;
    private String name;
    private String sureName;

    public User() {
    }

    public User(int userId, String name, String sureName) {
        this.userId = userId;
        this.name = name;
        this.sureName = sureName;
    }

    public User(String name, String sureName) {
        this.name = name;
        this.sureName = sureName;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getSureName() {
        return sureName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSureName(String sureName) {
        this.sureName = sureName;
    }

    @Override
    public String toString() {
        return userId + " " + name + " " + sureName;
    }
}
