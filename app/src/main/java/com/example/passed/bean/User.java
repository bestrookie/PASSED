package com.example.passed.bean;

import java.io.Serializable;

public class User implements Serializable {
    private String user_id;
    private String user_password;
    private long practice_id;

    public User(String user_id, String user_password) {
        this.user_id = user_id;
        this.user_password = user_password;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public long getPractice_id() {
        return practice_id;
    }

    public void setPractice_id(long practice_id) {
        this.practice_id = practice_id;
    }
}
