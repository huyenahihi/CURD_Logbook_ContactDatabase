package com.example.myapplication.model;

public class UserData {
    private int id;
    private String name;
    private String email;
    private String birthday;

    private String avatar;

    public UserData() {
    }

    public UserData(String name, String email, String birthday) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthday() {
        return birthday;
    }
}