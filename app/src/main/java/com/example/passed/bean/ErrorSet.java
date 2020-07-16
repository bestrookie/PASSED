package com.example.passed.bean;

public class ErrorSet {
    private String userId;
    private long exerciseId;

    public ErrorSet(String userId, long exerciseId) {
        this.userId = userId;
        this.exerciseId = exerciseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }
}
