package com.example.passed.bean;

public class CollectionSet {
    private String userId;
    private long exerciseId;

    public CollectionSet(String userId, long exerciseId) {
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
